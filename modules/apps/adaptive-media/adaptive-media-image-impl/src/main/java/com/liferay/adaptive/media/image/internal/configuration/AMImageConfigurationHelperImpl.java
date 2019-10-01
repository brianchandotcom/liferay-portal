/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.exception.AMImageConfigurationException.InvalidStateAMImageConfigurationException;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.constants.AMImageDestinationNames;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = AMImageConfigurationHelper.class)
public class AMImageConfigurationHelperImpl
	implements AMImageConfigurationHelper {

	@Override
	public AMImageConfigurationEntry addAMImageConfigurationEntry(
			long companyId, String name, String description, String uuid,
			Map<String, String> properties)
		throws AMImageConfigurationException, IOException {

		_checkName(name);
		_checkProperties(properties);

		_normalizeProperties(properties);

		_checkUuid(uuid);

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			getAMImageConfigurationEntries(
				companyId, amImageConfigurationEntry -> true);

		_checkDuplicatesName(amImageConfigurationEntries, name);

		_checkDuplicatesUuid(amImageConfigurationEntries, uuid);

		List<AMImageConfigurationEntry> updatedAMImageConfigurationEntries =
			new ArrayList<>(amImageConfigurationEntries);

		updatedAMImageConfigurationEntries.removeIf(
			amImageConfigurationEntry -> uuid.equals(
				amImageConfigurationEntry.getUUID()));

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				name, description, uuid, properties, true);

		updatedAMImageConfigurationEntries.add(amImageConfigurationEntry);

		_updateConfiguration(companyId, updatedAMImageConfigurationEntries);

		_triggerConfigurationEvent(amImageConfigurationEntry);

		return amImageConfigurationEntry;
	}

	@Override
	public void deleteAMImageConfigurationEntry(long companyId, String uuid)
		throws InvalidStateAMImageConfigurationException, IOException {

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			getAMImageConfigurationEntry(companyId, uuid);

		if (!amImageConfigurationEntryOptional.isPresent()) {
			return;
		}

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		if (amImageConfigurationEntry.isEnabled()) {
			throw new InvalidStateAMImageConfigurationException();
		}

		forceDeleteAMImageConfigurationEntry(companyId, uuid);
	}

	@Override
	public void disableAMImageConfigurationEntry(long companyId, String uuid)
		throws IOException {

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			getAMImageConfigurationEntry(companyId, uuid);

		if (!amImageConfigurationEntryOptional.isPresent()) {
			return;
		}

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		if (!amImageConfigurationEntry.isEnabled()) {
			return;
		}

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			getAMImageConfigurationEntries(
				companyId, curConfigurationEntry -> true);

		List<AMImageConfigurationEntry> updatedAMImageConfigurationEntries =
			new ArrayList<>(amImageConfigurationEntries);

		updatedAMImageConfigurationEntries.removeIf(
			curConfigurationEntry -> uuid.equals(
				curConfigurationEntry.getUUID()));

		AMImageConfigurationEntry newAMImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				amImageConfigurationEntry.getName(),
				amImageConfigurationEntry.getDescription(),
				amImageConfigurationEntry.getUUID(),
				amImageConfigurationEntry.getProperties(), false);

		updatedAMImageConfigurationEntries.add(newAMImageConfigurationEntry);

		_updateConfiguration(companyId, updatedAMImageConfigurationEntries);

		_triggerConfigurationEvent(amImageConfigurationEntry);
	}

	@Override
	public void enableAMImageConfigurationEntry(long companyId, String uuid)
		throws IOException {

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			getAMImageConfigurationEntry(companyId, uuid);

		if (!amImageConfigurationEntryOptional.isPresent()) {
			return;
		}

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		if (amImageConfigurationEntry.isEnabled()) {
			return;
		}

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			getAMImageConfigurationEntries(
				companyId, curConfigurationEntry -> true);

		List<AMImageConfigurationEntry> updatedAMImageConfigurationEntries =
			new ArrayList<>(amImageConfigurationEntries);

		updatedAMImageConfigurationEntries.removeIf(
			curConfigurationEntry -> uuid.equals(
				curConfigurationEntry.getUUID()));

		AMImageConfigurationEntry newAMImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				amImageConfigurationEntry.getName(),
				amImageConfigurationEntry.getDescription(),
				amImageConfigurationEntry.getUUID(),
				amImageConfigurationEntry.getProperties(), true);

		updatedAMImageConfigurationEntries.add(newAMImageConfigurationEntry);

		_updateConfiguration(companyId, updatedAMImageConfigurationEntries);

		_triggerConfigurationEvent(amImageConfigurationEntry);
	}

	@Override
	public void forceDeleteAMImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException {

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			getAMImageConfigurationEntry(companyId, uuid);

		if (!amImageConfigurationEntryOptional.isPresent()) {
			return;
		}

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		_amImageEntryLocalService.deleteAMImageEntries(
			companyId, amImageConfigurationEntry);

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			getAMImageConfigurationEntries(
				companyId, curConfigurationEntry -> true);

		List<AMImageConfigurationEntry> updatedAMImageConfigurationEntries =
			new ArrayList<>(amImageConfigurationEntries);

		updatedAMImageConfigurationEntries.removeIf(
			curConfigurationEntry -> uuid.equals(
				curConfigurationEntry.getUUID()));

		_updateConfiguration(companyId, updatedAMImageConfigurationEntries);

		_triggerConfigurationEvent(amImageConfigurationEntry);
	}

	@Override
	public Collection<AMImageConfigurationEntry> getAMImageConfigurationEntries(
		long companyId) {

		Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
			_getAMImageConfigurationEntries(companyId);

		return amImageConfigurationEntryStream.filter(
			AMImageConfigurationEntry::isEnabled
		).sorted(
			Comparator.comparing(AMImageConfigurationEntry::getName)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public Collection<AMImageConfigurationEntry> getAMImageConfigurationEntries(
		long companyId,
		Predicate<? super AMImageConfigurationEntry> predicate) {

		Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
			_getAMImageConfigurationEntries(companyId);

		return amImageConfigurationEntryStream.filter(
			predicate
		).sorted(
			Comparator.comparing(AMImageConfigurationEntry::getName)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public Optional<AMImageConfigurationEntry> getAMImageConfigurationEntry(
		long companyId, String configurationEntryUUID) {

		Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
			_getAMImageConfigurationEntries(companyId);

		return amImageConfigurationEntryStream.filter(
			amImageConfigurationEntry -> configurationEntryUUID.equals(
				amImageConfigurationEntry.getUUID())
		).findFirst();
	}

	@Override
	public AMImageConfigurationEntry updateAMImageConfigurationEntry(
			long companyId, String oldUuid, String name, String description,
			String newUuid, Map<String, String> properties)
		throws AMImageConfigurationException, IOException {

		_checkName(name);
		_checkProperties(properties);

		_normalizeProperties(properties);

		_checkUuid(newUuid);

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			getAMImageConfigurationEntries(
				companyId, amImageConfigurationEntry -> true);

		Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
			amImageConfigurationEntries.stream();

		Optional<AMImageConfigurationEntry>
			oldAMImageConfigurationEntryOptional =
				amImageConfigurationEntryStream.filter(
					amImageConfigurationEntry -> oldUuid.equals(
						amImageConfigurationEntry.getUUID())
				).findFirst();

		AMImageConfigurationEntry oldAMImageConfigurationEntry =
			oldAMImageConfigurationEntryOptional.orElseThrow(
				() ->
					new AMImageConfigurationException.
						NoSuchAMImageConfigurationException(
							"{uuid=" + oldUuid + "}"));

		if (!name.equals(oldAMImageConfigurationEntry.getName())) {
			_checkDuplicatesName(amImageConfigurationEntries, name);
		}

		if (!oldUuid.equals(newUuid)) {
			_checkDuplicatesUuid(amImageConfigurationEntries, newUuid);
		}

		List<AMImageConfigurationEntry> updatedAMImageConfigurationEntries =
			new ArrayList<>(amImageConfigurationEntries);

		updatedAMImageConfigurationEntries.removeIf(
			amImageConfigurationEntry -> oldUuid.equals(
				amImageConfigurationEntry.getUUID()));

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				name, description, newUuid, properties,
				oldAMImageConfigurationEntry.isEnabled());

		updatedAMImageConfigurationEntries.add(amImageConfigurationEntry);

		_updateConfiguration(companyId, updatedAMImageConfigurationEntries);

		_triggerConfigurationEvent(
			new AMImageConfigurationEntry[] {
				oldAMImageConfigurationEntry, amImageConfigurationEntry
			});

		return amImageConfigurationEntry;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				AMImageDestinationNames.ADAPTIVE_MEDIA_IMAGE_CONFIGURATION);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_destinationServiceRegistration.unregister();
	}

	private static final boolean _isPositiveNumber(String s) {
		Matcher matcher = _positiveNumberPattern.matcher(s);

		return matcher.matches();
	}

	private void _checkDuplicatesName(
			Collection<AMImageConfigurationEntry> amImageConfigurationEntries,
			String name)
		throws AMImageConfigurationException {

		Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
			amImageConfigurationEntries.stream();

		Optional<AMImageConfigurationEntry>
			duplicateNameAMImageConfigurationEntryOptional =
				amImageConfigurationEntryStream.filter(
					amImageConfigurationEntry -> name.equals(
						amImageConfigurationEntry.getName())
				).findFirst();

		if (duplicateNameAMImageConfigurationEntryOptional.isPresent()) {
			throw new AMImageConfigurationException.
				DuplicateAMImageConfigurationNameException();
		}
	}

	private void _checkDuplicatesUuid(
			Collection<AMImageConfigurationEntry> amImageConfigurationEntries,
			String uuid)
		throws AMImageConfigurationException {

		Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
			amImageConfigurationEntries.stream();

		Optional<AMImageConfigurationEntry>
			duplicateUuidAMImageConfigurationEntryOptional =
				amImageConfigurationEntryStream.filter(
					amImageConfigurationEntry -> uuid.equals(
						amImageConfigurationEntry.getUUID())
				).findFirst();

		if (duplicateUuidAMImageConfigurationEntryOptional.isPresent()) {
			throw new AMImageConfigurationException.
				DuplicateAMImageConfigurationUuidException();
		}
	}

	private void _checkName(String name) throws AMImageConfigurationException {
		if (Validator.isNull(name)) {
			throw new AMImageConfigurationException.InvalidNameException();
		}
	}

	private void _checkProperties(Map<String, String> properties)
		throws AMImageConfigurationException {

		String maxHeightString = properties.get("max-height");

		if (Validator.isNotNull(maxHeightString) &&
			!maxHeightString.equals("0") &&
			!_isPositiveNumber(maxHeightString)) {

			throw new AMImageConfigurationException.InvalidHeightException();
		}

		String maxWidthString = properties.get("max-width");

		if (Validator.isNotNull(maxWidthString) &&
			!maxWidthString.equals("0") && !_isPositiveNumber(maxWidthString)) {

			throw new AMImageConfigurationException.InvalidWidthException();
		}

		if ((Validator.isNull(maxHeightString) ||
			 maxHeightString.equals("0")) &&
			(Validator.isNull(maxWidthString) || maxWidthString.equals("0"))) {

			throw new AMImageConfigurationException.
				RequiredWidthOrHeightException();
		}
	}

	private void _checkUuid(String uuid) throws AMImageConfigurationException {
		if (Validator.isNull(uuid)) {
			throw new AMImageConfigurationException.InvalidUuidException();
		}

		Matcher matcher = _uuidPattern.matcher(uuid);

		if (!matcher.matches()) {
			throw new AMImageConfigurationException.InvalidUuidException();
		}
	}

	private Stream<AMImageConfigurationEntry> _getAMImageConfigurationEntries(
		long companyId) {

		if (_configurationEntries.containsKey(companyId)) {
			Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
				_configurationEntries.get(companyId);

			return amImageConfigurationEntries.stream();
		}

		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId, AMImageCompanyConfiguration.class.getName()));

			Optional<String[]> nullableImageVariants =
				_getNullableImageVariants(settings);

			String[] imageVariants = nullableImageVariants.orElseGet(
				() -> settings.getValues("imageVariants", new String[0]));

			List<AMImageConfigurationEntry> amImageConfigurationEntries =
				Stream.of(
					imageVariants
				).map(
					_amImageConfigurationEntryParser::parse
				).collect(
					Collectors.toList()
				);

			_configurationEntries.put(companyId, amImageConfigurationEntries);

			return amImageConfigurationEntries.stream();
		}
		catch (SettingsException se) {
			throw new AMRuntimeException.InvalidConfiguration(se);
		}
	}

	private Optional<String[]> _getNullableImageVariants(Settings settings) {
		PortletPreferencesSettings portletPreferencesSettings =
			(PortletPreferencesSettings)settings;

		PortletPreferences portletPreferences =
			portletPreferencesSettings.getPortletPreferences();

		Map<String, String[]> map = portletPreferences.getMap();

		return Optional.ofNullable(map.get("imageVariants"));
	}

	private void _normalizeProperties(Map<String, String> properties) {
		String maxHeightString = properties.get("max-height");
		String maxWidthString = properties.get("max-width");

		if (Validator.isNotNull(maxHeightString) &&
			Validator.isNotNull(maxWidthString)) {

			return;
		}

		if (Validator.isNull(maxHeightString)) {
			properties.put("max-height", "0");
		}

		if (Validator.isNull(maxWidthString)) {
			properties.put("max-width", "0");
		}
	}

	private void _triggerConfigurationEvent(Object payload) {
		Message message = new Message();

		message.setPayload(payload);

		_messageBus.sendMessage(
			AMImageDestinationNames.ADAPTIVE_MEDIA_IMAGE_CONFIGURATION,
			message);
	}

	private void _updateConfiguration(
			long companyId,
			List<AMImageConfigurationEntry> amImageConfigurationEntries)
		throws IOException {

		try {
			Settings settings = SettingsFactoryUtil.getSettings(
				new CompanyServiceSettingsLocator(
					companyId, AMImageCompanyConfiguration.class.getName()));

			ModifiableSettings modifiableSettings =
				settings.getModifiableSettings();

			Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
				amImageConfigurationEntries.stream();

			List<String> imageVariants = amImageConfigurationEntryStream.map(
				_amImageConfigurationEntryParser::getConfigurationString
			).collect(
				Collectors.toList()
			);

			modifiableSettings.setValues(
				"imageVariants", imageVariants.toArray(new String[0]));

			modifiableSettings.store();

			amImageConfigurationEntryStream =
				amImageConfigurationEntries.stream();

			_configurationEntries.put(
				companyId,
				amImageConfigurationEntryStream.collect(Collectors.toList()));
		}
		catch (SettingsException | ValidatorException e) {
			throw new AMRuntimeException.InvalidConfiguration(e);
		}
	}

	private static final Pattern _positiveNumberPattern = Pattern.compile(
		"\\d*[1-9]\\d*");
	private static final Pattern _uuidPattern = Pattern.compile("^(?:\\w|-)+$");

	@Reference
	private AMImageConfigurationEntryParser _amImageConfigurationEntryParser;

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

	private final Map<Long, Collection<AMImageConfigurationEntry>>
		_configurationEntries = new ConcurrentHashMap<>();

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

	@Reference
	private MessageBus _messageBus;

}