/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemContextReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemReference;
import com.liferay.headless.admin.site.client.dto.v1_0.Mapping;
import com.liferay.headless.admin.site.client.scope.Scope;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentMappedValueTestUtil {

	public static FragmentMappedValue getFragmentMappedValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		String fieldKey, FragmentMappedValueItemReference.Type type) {

		return getFragmentMappedValue(
			fieldKey,
			_getFragmentMappedValueItemReference(contextSource, type));
	}

	public static FragmentMappedValue getFragmentMappedValue(
		String fieldKey,
		FragmentMappedValueItemReference fragmentMappedValueItemReference) {

		FragmentMappedValue fragmentMappedValue = new FragmentMappedValue();

		Mapping mapping = new Mapping();

		mapping.setFieldKey(() -> fieldKey);
		mapping.setItemReference(() -> fragmentMappedValueItemReference);

		fragmentMappedValue.setMapping(() -> mapping);

		return fragmentMappedValue;
	}

	public static FragmentMappedValue getFragmentMappedValue(
		String className, String externalReferenceCode, String fieldKey,
		String scopeExternalReferenceCode) {

		return getFragmentMappedValue(
			fieldKey,
			_getFragmentMappedValueItemExternalReference(
				className, externalReferenceCode, scopeExternalReferenceCode));
	}

	private static FragmentMappedValueItemContextReference
		_getFragmentMappedValueItemContextReference(
			FragmentMappedValueItemContextReference.ContextSource
				contextSource) {

		FragmentMappedValueItemContextReference
			fragmentMappedValueItemContextReference =
				new FragmentMappedValueItemContextReference();

		fragmentMappedValueItemContextReference.setContextSource(contextSource);
		fragmentMappedValueItemContextReference.setType(
			FragmentMappedValueItemReference.Type.CONTEXT_REFERENCE);

		return fragmentMappedValueItemContextReference;
	}

	private static FragmentMappedValueItemExternalReference
		_getFragmentMappedValueItemExternalReference(
			String className, String externalReferenceCode,
			String scopeExternalReferenceCode) {

		FragmentMappedValueItemExternalReference
			fragmentMappedValueItemExternalReference =
				new FragmentMappedValueItemExternalReference() {
					{
						setType(Type.ITEM_EXTERNAL_REFERENCE);
					}
				};

		fragmentMappedValueItemExternalReference.setClassName(() -> className);
		fragmentMappedValueItemExternalReference.setExternalReferenceCode(
			() -> externalReferenceCode);
		fragmentMappedValueItemExternalReference.setScope(
			() -> {
				if (Validator.isNull(scopeExternalReferenceCode)) {
					return null;
				}

				Scope scope = new Scope();

				scope.setExternalReferenceCode(
					() -> scopeExternalReferenceCode);
				scope.setType(() -> Scope.Type.SITE);

				return scope;
			});

		return fragmentMappedValueItemExternalReference;
	}

	private static FragmentMappedValueItemReference
		_getFragmentMappedValueItemReference(
			FragmentMappedValueItemContextReference.ContextSource contextSource,
			FragmentMappedValueItemReference.Type type) {

		if (type == FragmentMappedValueItemReference.Type.CONTEXT_REFERENCE) {
			return _getFragmentMappedValueItemContextReference(contextSource);
		}

		if (type ==
				FragmentMappedValueItemReference.Type.ITEM_EXTERNAL_REFERENCE) {

			return _getFragmentMappedValueItemExternalReference(
				FileEntry.class.getName(), RandomTestUtil.randomString(), null);
		}

		return null;
	}

}