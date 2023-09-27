/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.osb.faro.engine.client.model.Credentials;
import com.liferay.osb.faro.engine.client.model.DataSource;
import com.liferay.osb.faro.engine.client.model.Event;
import com.liferay.osb.faro.engine.client.model.Provider;
import com.liferay.osb.faro.engine.client.model.provider.CSVProvider;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.osb.faro.web.internal.constants.FaroConstants;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DataSourceDisplay implements FaroEntityDisplay {

	public DataSourceDisplay() {
	}

	public DataSourceDisplay(long groupId, DataSource dataSource) {
		_credentials = dataSource.getCredentials();

		if (_credentials != null) {
			_credentials.clearPasswords();
		}

		_createDate = dataSource.getCreateDate();

		if (dataSource.getContactsSyncDetails() != null) {
			DataSource.Details details = dataSource.getContactsSyncDetails();

			_contactsSelected = details.isSelected();
		}

		if (dataSource.getSitesSyncDetails() != null) {
			DataSource.Details details = dataSource.getSitesSyncDetails();

			_sitesSelected = details.isSelected();
		}

		_disabled = false;
		_event = dataSource.getSubjectOf();
		_id = dataSource.getId();
		_name = dataSource.getName();

		_provider = dataSource.getProvider();

		_providerType = _provider.getType();

		if (_providerType.equals(CSVProvider.TYPE)) {
			DLFileEntry dlFileEntry =
				DLFileEntryLocalServiceUtil.fetchFileEntry(groupId, 0, _id);

			if (dlFileEntry != null) {
				_fileName = dlFileEntry.getDescription();
			}
		}

		_state = dataSource.getState();
		_status = dataSource.getStatus();
		_type = FaroConstants.TYPE_DATA_SOURCE;
		_url = dataSource.getUrl();
	}

	@Override
	public void addProperties(List<String> propertyNames) {
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Map<String, Object> getProperties() {
		return Collections.emptyMap();
	}

	@Override
	public int getType() {
		return _type;
	}

	protected List<Long> getContainerIds(
		List<LiferayProvider.Container> containers) {

		return TransformUtil.transform(
			containers, container -> GetterUtil.getLong(container.getId()));
	}

	private Boolean _contactsSelected;
	private Date _createDate;
	private Credentials _credentials;
	private boolean _disabled;
	private Event _event;
	private String _fileName;
	private String _id;
	private String _name;
	private Provider _provider;
	private String _providerType;
	private Boolean _sitesSelected;
	private String _state;
	private String _status;
	private int _type;
	private String _url;

}