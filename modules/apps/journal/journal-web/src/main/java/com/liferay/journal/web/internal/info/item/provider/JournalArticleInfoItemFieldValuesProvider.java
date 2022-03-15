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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMFormValuesInfoFieldValuesProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.request.attributes.contributor.InfoDisplayRequestAttributesContributor;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.WebImage;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.internal.asset.JournalArticleDDMFormValuesReader;
import com.liferay.journal.web.internal.info.item.JournalArticleInfoItemFields;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFieldValuesProvider.class
)
public class JournalArticleInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<JournalArticle> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		JournalArticle journalArticle) {

		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getJournalArticleInfoFieldValues(journalArticle)
			).infoFieldValues(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey())
			).infoFieldValues(
				_expandoInfoItemFieldSetProvider.getInfoFieldValues(
					JournalArticle.class.getName(), journalArticle)
			).infoFieldValues(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
					JournalArticle.class.getName(), journalArticle)
			).infoFieldValues(
				_getDDMStructureInfoFieldValues(journalArticle)
			).infoFieldValues(
				_getDDMTemplateInfoFieldValues(journalArticle)
			).infoFieldValues(
				_templateInfoItemFieldSetProvider.getInfoFieldValues(
					JournalArticle.class.getName(),
					_getInfoItemFormVariationKey(journalArticle),
					journalArticle)
			).infoItemReference(
				new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey())
			).build();
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Caught unexpected exception", noSuchInfoItemException);
		}
	}

	private List<InfoFieldValue<Object>> _getDDMStructureInfoFieldValues(
		JournalArticle article) {

		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			_fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(
			_journalConverter);

		try {
			return _ddmFormValuesInfoFieldValuesProvider.getInfoFieldValues(
				article, journalArticleDDMFormValuesReader.getDDMFormValues());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private List<InfoFieldValue<Object>> _getDDMTemplateInfoFieldValues(
		JournalArticle journalArticle) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		ddmTemplates.forEach(
			ddmTemplate -> {
				String fieldName = _getTemplateKey(ddmTemplate);

				infoFieldValues.add(
					_getJournalTemplateInfoFieldValue(
						ddmTemplate, fieldName, journalArticle));
			});

		return infoFieldValues;
	}

	private String _getDisplayPageURL(
			JournalArticle journalArticle, ThemeDisplay themeDisplay)
		throws PortalException {

		String friendlyURL =
			_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey(), themeDisplay);

		if (Validator.isNotNull(friendlyURL)) {
			return friendlyURL;
		}

		if (Validator.isNull(journalArticle.getLayoutUuid())) {
			return StringPool.BLANK;
		}

		Layout layout = journalArticle.getLayout();

		String groupFriendlyURL = _portal.getGroupFriendlyURL(
			_layoutSetLocalService.getLayoutSet(
				journalArticle.getGroupId(), layout.isPrivateLayout()),
			themeDisplay, false, false);

		return StringBundler.concat(
			groupFriendlyURL, JournalArticleConstants.CANONICAL_URL_SEPARATOR,
			journalArticle.getUrlTitle(themeDisplay.getLocale()));
	}

	private String _getInfoItemFormVariationKey(JournalArticle journalArticle) {
		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return String.valueOf(ddmStructure.getStructureId());
	}

	private List<InfoFieldValue<Object>> _getJournalArticleInfoFieldValues(
		JournalArticle journalArticle) {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		try {
			List<InfoFieldValue<Object>> journalArticleFieldValues =
				new ArrayList<>();

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.titleInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							journalArticle.getDefaultLanguageId())
					).values(
						journalArticle.getTitleMap()
					).build()));
			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.descriptionInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							journalArticle.getDefaultLanguageId())
					).values(
						journalArticle.getDescriptionMap()
					).build()));
			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.createDateInfoField,
					journalArticle.getCreateDate()));
			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.modifiedDateInfoField,
					journalArticle.getModifiedDate()));

			if (themeDisplay != null) {
				String articleImageURL = journalArticle.getArticleImageURL(
					themeDisplay);

				if (Validator.isNotNull(articleImageURL)) {
					journalArticleFieldValues.add(
						new InfoFieldValue<>(
							JournalArticleInfoItemFields.smallImageInfoField,
							new WebImage(articleImageURL)));
				}
			}

			User user = _userLocalService.fetchUser(journalArticle.getUserId());

			if (user != null) {
				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						JournalArticleInfoItemFields.authorNameInfoField,
						user.getFullName()));

				if (themeDisplay != null) {
					WebImage webImage = new WebImage(
						user.getPortraitURL(themeDisplay));

					webImage.setAlt(user.getFullName());

					journalArticleFieldValues.add(
						new InfoFieldValue<>(
							JournalArticleInfoItemFields.
								authorProfileImageInfoField,
							webImage));
				}
			}

			User lastEditorUser = _userLocalService.fetchUser(
				journalArticle.getStatusByUserId());

			if (lastEditorUser != null) {
				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						JournalArticleInfoItemFields.lastEditorNameInfoField,
						lastEditorUser.getFullName()));

				if (themeDisplay != null) {
					WebImage webImage = new WebImage(
						lastEditorUser.getPortraitURL(themeDisplay));

					webImage.setAlt(lastEditorUser.getFullName());

					journalArticleFieldValues.add(
						new InfoFieldValue<>(
							JournalArticleInfoItemFields.
								lastEditorProfileImageInfoField,
							webImage));
				}
			}

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.displayDateInfoField,
					journalArticle.getDisplayDate()));
			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.expirationDateInfoField,
					journalArticle.getExpirationDate()));
			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.publishDateInfoField,
					journalArticle.getDisplayDate()));

			if (themeDisplay != null) {
				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						JournalArticleInfoItemFields.displayPageURLInfoField,
						_getDisplayPageURL(journalArticle, themeDisplay)));
			}

			return journalArticleFieldValues;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private InfoFieldValue<Object> _getJournalTemplateInfoFieldValue(
		DDMTemplate ddmTemplate, String fieldName,
		JournalArticle journalArticle) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		String languageId = LocaleUtil.toLanguageId(locale);

		return new InfoFieldValue<>(
			InfoField.builder(
			).infoFieldType(
				TextInfoFieldType.INSTANCE
			).name(
				fieldName
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), fieldName)
			).build(),
			() -> {
				ThemeDisplay themeDisplay = _getThemeDisplay();

				HttpServletRequest httpServletRequest =
					themeDisplay.getRequest();

				InfoItemDetailsProvider infoItemDetailsProvider =
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemDetailsProvider.class,
						JournalArticle.class.getName());

				httpServletRequest.setAttribute(
					InfoDisplayWebKeys.INFO_ITEM_DETAILS,
					infoItemDetailsProvider.getInfoItemDetails(journalArticle));

				for (InfoDisplayRequestAttributesContributor
						infoDisplayRequestAttributesContributor :
							_infoDisplayRequestAttributesContributors) {

					infoDisplayRequestAttributesContributor.addAttributes(
						httpServletRequest);
				}

				PortletRequestModel portletRequestModel = null;

				PortletRequest portletRequest =
					(PortletRequest)httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST);

				PortletResponse portletResponse =
					(PortletResponse)httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE);

				if ((portletRequest != null) && (portletResponse != null)) {
					portletRequestModel = new PortletRequestModel(
						portletRequest, portletResponse);
				}

				JournalArticleDisplay journalArticleDisplay =
					_journalContent.getDisplay(
						journalArticle, ddmTemplate.getTemplateKey(),
						com.liferay.portal.kernel.util.Constants.VIEW,
						languageId, 1, portletRequestModel, themeDisplay);

				if (journalArticleDisplay != null) {
					return journalArticleDisplay.getContent();
				}

				try {
					journalArticleDisplay =
						_journalArticleLocalService.getArticleDisplay(
							journalArticle, ddmTemplate.getTemplateKey(), null,
							languageId, 1, null, themeDisplay);

					return journalArticleDisplay.getContent();
				}
				catch (Exception exception) {
					throw new RuntimeException(
						"Unable to render dynamic data mapping template " +
							ddmTemplate.getTemplateId(),
						exception);
				}
			});
	}

	private String _getTemplateKey(DDMTemplate ddmTemplate) {
		String templateKey = ddmTemplate.getTemplateKey();

		return PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
			templateKey.replaceAll("\\W", "_");
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private DDMFormValuesInfoFieldValuesProvider
		_ddmFormValuesInfoFieldValuesProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private volatile List<InfoDisplayRequestAttributesContributor>
		_infoDisplayRequestAttributesContributors;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

	@Reference
	private UserLocalService _userLocalService;

}