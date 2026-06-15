/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.data.masking.internal.model.listener;

import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.headless.data.masking.engine.DataMaskingEngine;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.security.auth.PrincipalException;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jose Luis Navarro
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class DataMaskObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return _EXTERNAL_REFERENCE_CODE_DATA_MASK;
	}

	@Override
	public void onAfterRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		_evictPatterns(objectEntry);
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_evictPatterns(originalObjectEntry);
	}

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		if (_isDataMaskingSeedImport()) {
			return;
		}

		Map<String, Serializable> values = objectEntry.getValues();

		if (Objects.equals(values.get("maskType"), "system")) {
			throw new ModelListenerException(
				new PrincipalException("Unable to create system data masks"));
		}
	}

	@Override
	public void onBeforeRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		if (_isDataMaskingSeedImport()) {
			return;
		}

		Map<String, Serializable> values = objectEntry.getValues();

		if (Objects.equals(values.get("maskType"), "system")) {
			throw new ModelListenerException(
				new PrincipalException("Unable to delete system data masks"));
		}
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		if (_isDataMaskingSeedImport()) {
			return;
		}

		Map<String, Serializable> originalValues =
			originalObjectEntry.getValues();

		if (Objects.equals(originalValues.get("maskType"), "system")) {
			throw new ModelListenerException(
				new PrincipalException("Unable to update system data masks"));
		}
	}

	private void _evictPatterns(ObjectEntry objectEntry) {
		Map<String, Serializable> values = objectEntry.getValues();

		_dataMaskingEngine.evictPattern((String)values.get("detectionRegex"));
		_dataMaskingEngine.evictPattern((String)values.get("replacementRegex"));
	}

	private boolean _isDataMaskingSeedImport() {
		String fileName = BatchEngineUnitThreadLocal.getFileName();

		return fileName.startsWith(_BUNDLE_SYMBOLIC_NAME + "_");
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.headless.data.masking.impl";

	private static final String _EXTERNAL_REFERENCE_CODE_DATA_MASK =
		"L_DATA_MASK";

	@Reference
	private DataMaskingEngine _dataMaskingEngine;

}