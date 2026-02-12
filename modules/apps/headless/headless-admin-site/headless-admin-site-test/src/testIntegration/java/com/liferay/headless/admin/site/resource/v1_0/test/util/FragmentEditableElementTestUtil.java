/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.ActionFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ActionInteraction;
import com.liferay.headless.admin.site.client.dto.v1_0.BackgroundImageFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageActionInteraction;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElement;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElementValueFragmentLink;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentImage;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentImageViewport;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentLink;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentLinkTextValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemContextReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemReference;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentInlineValue;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ImageFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.LinkFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.NoneActionInteraction;
import com.liferay.headless.admin.site.client.dto.v1_0.NotificationActionInteraction;
import com.liferay.headless.admin.site.client.dto.v1_0.PageActionInteraction;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentInlineValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentValue;
import com.liferay.headless.admin.site.client.dto.v1_0.URLActionInteraction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author Rubén Pulido
 */
public class FragmentEditableElementTestUtil {

	public static FragmentEditableElement getActionFragmentEditableElement(
		ActionInteraction errorActionInteraction,
		FragmentMappedValue fragmentMappedValue, String id,
		ActionInteraction successActionInteraction,
		TextFragmentValue textFragmentValue) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		ActionFragmentEditableElementValue actionFragmentEditableElementValue =
			new ActionFragmentEditableElementValue();

		actionFragmentEditableElementValue.setErrorActionInteraction(
			() -> errorActionInteraction);
		actionFragmentEditableElementValue.setFragmentMappedValue(
			() -> fragmentMappedValue);
		actionFragmentEditableElementValue.setSuccessActionInteraction(
			() -> successActionInteraction);
		actionFragmentEditableElementValue.setTextFragmentValue(
			() -> textFragmentValue);
		actionFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.ACTION);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> actionFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static FragmentEditableElement
		getBackgroundImageFragmentEditableElement(
			FragmentImageValue fragmentImageValue, String id) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		BackgroundImageFragmentEditableElementValue
			backgroundImageFragmentEditableElementValue =
				new BackgroundImageFragmentEditableElementValue();

		backgroundImageFragmentEditableElementValue.
			setBackgroundFragmentImageValue(() -> fragmentImageValue);
		backgroundImageFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.BACKGROUND_IMAGE);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> backgroundImageFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static DisplayPageActionInteraction
		getDisplayPageActionInteraction() {

		DisplayPageActionInteraction displayPageActionInteraction =
			new DisplayPageActionInteraction();

		displayPageActionInteraction.setMappingFieldKey(
			RandomTestUtil::randomString);
		displayPageActionInteraction.setType(
			() -> ActionInteraction.Type.DISPLAY_PAGE);

		return displayPageActionInteraction;
	}

	public static FragmentImage getFragmentImage(
		Map<String, String> descriptionMap,
		FragmentImageValue fragmentImageValue, Boolean lazyLoading,
		Map<String, String> resolutionMap) {

		FragmentImage fragmentImage = new FragmentImage();

		fragmentImage.setDescription_i18n(() -> descriptionMap);
		fragmentImage.setFragmentImageValue(() -> fragmentImageValue);
		fragmentImage.setFragmentImageViewports(
			() -> {
				FragmentImageViewport[] fragmentImageViewports =
					TransformUtil.transformToArray(
						new TreeSet<>(resolutionMap.keySet()),
						key -> {
							FragmentImageViewport.Id id =
								FragmentImageViewport.Id.create(key);

							if (id == null) {
								return null;
							}

							FragmentImageViewport fragmentImageViewport =
								new FragmentImageViewport();

							fragmentImageViewport.setId(() -> id);
							fragmentImageViewport.setResolution(
								() -> resolutionMap.get(key));

							return fragmentImageViewport;
						},
						FragmentImageViewport.class);

				if (ArrayUtil.isEmpty(fragmentImageViewports)) {
					return null;
				}

				return fragmentImageViewports;
			});
		fragmentImage.setLazyLoading(() -> lazyLoading);

		return fragmentImage;
	}

	public static FragmentEditableElement getHTMLFragmentEditableElement(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentEditableElementValue.Type fragmentEditableElementValueType,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		HTMLFragmentValue.Type htmlFragmentValueType, String id) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		HTMLFragmentEditableElementValue htmlFragmentEditableElementValue =
			new HTMLFragmentEditableElementValue();

		htmlFragmentEditableElementValue.setHtmlFragmentValue(
			() -> _getHTMLFragmentValue(
				contextSource, fragmentMappedValueItemReferenceType,
				htmlFragmentValueType));
		htmlFragmentEditableElementValue.setType(
			() -> fragmentEditableElementValueType);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> htmlFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static FragmentEditableElement getImageFragmentEditableElement(
		FragmentImage fragmentImage, String id) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		ImageFragmentEditableElementValue imageFragmentEditableElementValue =
			new ImageFragmentEditableElementValue();

		imageFragmentEditableElementValue.setFragmentImage(() -> fragmentImage);
		imageFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.IMAGE);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> imageFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static FragmentEditableElement getLinkFragmentEditableElement(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentLink fragmentLink,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		String id, FragmentEditableElementValueFragmentLink.Prefix prefix,
		TextFragmentValue.Type textFragmentValueType) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		LinkFragmentEditableElementValue linkFragmentEditableElementValue =
			new LinkFragmentEditableElementValue();

		linkFragmentEditableElementValue.setFragmentLinkTextValue(
			() -> _getFragmentLinkTextValue(
				contextSource, fragmentLink,
				fragmentMappedValueItemReferenceType, prefix,
				textFragmentValueType));
		linkFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.LINK);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> linkFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static NoneActionInteraction getNoneActionInteraction() {
		NoneActionInteraction noneActionInteraction =
			new NoneActionInteraction();

		noneActionInteraction.setReload(RandomTestUtil::randomBoolean);
		noneActionInteraction.setType(() -> ActionInteraction.Type.NONE);

		return noneActionInteraction;
	}

	public static NotificationActionInteraction
		getNotificationActionInteraction() {

		NotificationActionInteraction notificationActionInteraction =
			new NotificationActionInteraction();

		notificationActionInteraction.setFragmentInlineValue(
			() -> _getFragmentInlineValue());
		notificationActionInteraction.setReload(RandomTestUtil::randomBoolean);
		notificationActionInteraction.setType(
			() -> ActionInteraction.Type.NOTIFICATION);

		return notificationActionInteraction;
	}

	public static PageActionInteraction getPageActionInteraction(
		ItemExternalReference itemExternalReference) {

		PageActionInteraction pageActionInteraction =
			new PageActionInteraction();

		pageActionInteraction.setItemExternalReference(
			() -> itemExternalReference);
		pageActionInteraction.setType(() -> ActionInteraction.Type.PAGE);

		return pageActionInteraction;
	}

	public static FragmentEditableElement getTextFragmentEditableElement(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentLink fragmentLink,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		FragmentEditableElementValueFragmentLink.Prefix prefix,
		TextFragmentValue.Type textFragmentValueType) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		TextFragmentEditableElementValue textFragmentEditableElementValue =
			new TextFragmentEditableElementValue();

		textFragmentEditableElementValue.setFragmentLinkTextValue(
			() -> _getFragmentLinkTextValue(
				contextSource, fragmentLink,
				fragmentMappedValueItemReferenceType, prefix,
				textFragmentValueType));
		textFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.TEXT);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> textFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> "element-text");

		return fragmentEditableElement;
	}

	public static FragmentEditableElement getTextFragmentEditableElement(
		String className,
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		String externalReferenceCode, String fieldKey,
		String scopeExternalReferenceCode) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		TextFragmentEditableElementValue textFragmentEditableElementValue =
			new TextFragmentEditableElementValue();

		FragmentMappedValue fragmentMappedValue = null;

		if (contextSource == null) {
			fragmentMappedValue =
				FragmentMappedValueTestUtil.getFragmentMappedValue(
					className, externalReferenceCode, fieldKey,
					scopeExternalReferenceCode);
		}
		else {
			fragmentMappedValue =
				FragmentMappedValueTestUtil.getFragmentMappedValue(
					contextSource, fieldKey,
					FragmentMappedValueItemReference.Type.CONTEXT_REFERENCE);
		}

		TextFragmentMappedValue textFragmentMappedValue =
			getTextFragmentMappedValue(fragmentMappedValue);

		textFragmentEditableElementValue.setFragmentLinkTextValue(
			new FragmentLinkTextValue() {
				{
					setTextFragmentValue(textFragmentMappedValue);
				}
			});

		textFragmentEditableElementValue.setType(
			FragmentEditableElementValue.Type.TEXT);

		fragmentEditableElement.setFragmentEditableElementValue(
			textFragmentEditableElementValue);

		fragmentEditableElement.setId("element-text");

		return fragmentEditableElement;
	}

	public static TextFragmentInlineValue getTextFragmentInlineValue() {
		return new TextFragmentInlineValue() {
			{
				setFragmentInlineValue(() -> _getFragmentInlineValue());
				setType(Type.INLINE);
			}
		};
	}

	public static TextFragmentMappedValue getTextFragmentMappedValue(
		FragmentMappedValue fragmentMappedValue) {

		TextFragmentMappedValue textFragmentMappedValue =
			new TextFragmentMappedValue();

		textFragmentMappedValue.setFragmentMappedValue(
			() -> fragmentMappedValue);
		textFragmentMappedValue.setType(() -> TextFragmentValue.Type.MAPPED);

		return textFragmentMappedValue;
	}

	public static URLActionInteraction getURLActionInteraction() {
		URLActionInteraction urlActionInteraction = new URLActionInteraction();

		urlActionInteraction.setFragmentInlineValue(
			() -> _getFragmentInlineValue());
		urlActionInteraction.setType(() -> ActionInteraction.Type.URL);

		return urlActionInteraction;
	}

	private static FragmentEditableElementValueFragmentLink
		_getFragmentEditableElementValueFragmentLink(
			FragmentLink fragmentLink,
			FragmentEditableElementValueFragmentLink.Prefix prefix) {

		if (fragmentLink == null) {
			return null;
		}

		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink =
				new FragmentEditableElementValueFragmentLink();

		fragmentEditableElementValueFragmentLink.setFragmentLink(
			() -> fragmentLink);
		fragmentEditableElementValueFragmentLink.setPrefix(() -> prefix);

		return fragmentEditableElementValueFragmentLink;
	}

	private static FragmentInlineValue _getFragmentInlineValue() {
		FragmentInlineValue fragmentInlineValue = new FragmentInlineValue();

		fragmentInlineValue.setValue_i18n(
			() -> HashMapBuilder.put(
				"en-US", RandomTestUtil.randomString()
			).put(
				"es-ES", RandomTestUtil.randomString()
			).build());

		return fragmentInlineValue;
	}

	private static FragmentLinkTextValue _getFragmentLinkTextValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentLink fragmentLink,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		FragmentEditableElementValueFragmentLink.Prefix prefix,
		TextFragmentValue.Type textFragmentValueType) {

		return new FragmentLinkTextValue() {
			{
				setFragmentEditableElementValueFragmentLink(
					() -> _getFragmentEditableElementValueFragmentLink(
						fragmentLink, prefix));
				setTextFragmentValue(
					() -> _getTextFragmentValue(
						contextSource, fragmentMappedValueItemReferenceType,
						textFragmentValueType));
			}
		};
	}

	private static HTMLFragmentInlineValue _getHTMLFragmentInlineValue() {
		return new HTMLFragmentInlineValue() {
			{
				setFragmentInlineValue(() -> _getFragmentInlineValue());
				setType(Type.INLINE);
			}
		};
	}

	private static HTMLFragmentMappedValue _getHTMLFragmentMappedValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType) {

		return new HTMLFragmentMappedValue() {
			{
				setFragmentMappedValue(
					() -> FragmentMappedValueTestUtil.getFragmentMappedValue(
						contextSource, "field-key",
						fragmentMappedValueItemReferenceType));
				setType(Type.MAPPED);
			}
		};
	}

	private static HTMLFragmentValue _getHTMLFragmentValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		HTMLFragmentValue.Type htmlFragmentValueType) {

		if (htmlFragmentValueType == HTMLFragmentValue.Type.INLINE) {
			return _getHTMLFragmentInlineValue();
		}

		if (htmlFragmentValueType == HTMLFragmentValue.Type.MAPPED) {
			return _getHTMLFragmentMappedValue(
				contextSource, fragmentMappedValueItemReferenceType);
		}

		return null;
	}

	private static TextFragmentMappedValue _getTextFragmentMappedValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType) {

		return new TextFragmentMappedValue() {
			{
				setFragmentMappedValue(
					() -> FragmentMappedValueTestUtil.getFragmentMappedValue(
						contextSource, "field-key",
						fragmentMappedValueItemReferenceType));
				setType(Type.MAPPED);
			}
		};
	}

	private static TextFragmentValue _getTextFragmentValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		TextFragmentValue.Type textFragmentValueType) {

		if (textFragmentValueType == TextFragmentValue.Type.INLINE) {
			return getTextFragmentInlineValue();
		}

		if (textFragmentValueType == TextFragmentValue.Type.MAPPED) {
			return _getTextFragmentMappedValue(
				contextSource, fragmentMappedValueItemReferenceType);
		}

		return null;
	}

}