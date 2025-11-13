/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElement;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemContextReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemReference;
import com.liferay.headless.admin.site.client.dto.v1_0.Mapping;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextInlineFragmentValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextMappedFragmentValue;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

/**
 * @author Rubén Pulido
 */
public class FragmentEditableElementTestUtil {

	public static FragmentEditableElement[] getFragmentEditableElements(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		TextFragmentValue.Type textFragmentValueType) {

		return new FragmentEditableElement[] {
			new FragmentEditableElement() {
				{
					setFragmentEditableElementValue(
						() -> new TextFragmentEditableElementValue() {
							{
								setTextFragmentValue(
									() -> _getTextFragmentValue(
										contextSource,
										fragmentMappedValueItemReferenceType,
										textFragmentValueType));
								setType(Type.TEXT);
							}
						});
					setId("element-text");
				}
			}
		};
	}

	private static FragmentMappedValueItemContextReference
		_getFragmentMappedValueItemContextReference(
			FragmentMappedValueItemContextReference.ContextSource
				curContextSource) {

		return new FragmentMappedValueItemContextReference() {
			{
				setContextSource(curContextSource);
				setType(Type.CONTEXT_REFERENCE);
			}
		};
	}

	private static FragmentMappedValueItemExternalReference
		_getFragmentMappedValueItemExternalReference() {

		return new FragmentMappedValueItemExternalReference() {
			{
				setClassName(FileEntry.class.getName());
				setExternalReferenceCode(RandomTestUtil.randomString());
				setType(Type.ITEM_EXTERNAL_REFERENCE);
			}
		};
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

			return _getFragmentMappedValueItemExternalReference();
		}

		return null;
	}

	private static TextFragmentValue _getTextFragmentValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		TextFragmentValue.Type textFragmentValueType) {

		if (textFragmentValueType == TextFragmentValue.Type.INLINE) {
			return _getTextInlineFragmentValue();
		}

		if (textFragmentValueType == TextFragmentValue.Type.MAPPED) {
			return _getTextMappedFragmentValue(
				contextSource, fragmentMappedValueItemReferenceType);
		}

		return null;
	}

	private static TextInlineFragmentValue _getTextInlineFragmentValue() {
		return new TextInlineFragmentValue() {
			{
				setFragmentInlineValue(
					() -> {
						FragmentInlineValue fragmentInlineValue =
							new FragmentInlineValue();

						fragmentInlineValue.setValue_i18n(
							() -> HashMapBuilder.put(
								"en-US", RandomTestUtil.randomString()
							).put(
								"es-ES", RandomTestUtil.randomString()
							).build());

						return fragmentInlineValue;
					});
				setType(Type.INLINE);
			}
		};
	}

	private static TextMappedFragmentValue _getTextMappedFragmentValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type curType) {

		return new TextMappedFragmentValue() {
			{
				setFragmentMappedValue(
					() -> new FragmentMappedValue() {
						{
							setMapping(
								new Mapping() {
									{
										setFieldKey("field-key");
										setItemReference(
											_getFragmentMappedValueItemReference(
												contextSource, curType));
									}
								});
						}
					});
				setType(Type.MAPPED);
			}
		};
	}

}