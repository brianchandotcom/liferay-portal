<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<div class="row">
	<div class="col-md-12">
		<aui:input checked="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED) %>" id="enablePrivateLayouts" inlineLabel="right" label="enable-private-layouts" labelCssClass="simple-toggle-switch" name='<%= "settings--" + PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED + "--" %>' onClick="togglePrivateLayoutsAutoCreate()" type="toggle-switch" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED) %>" />

		<aui:input checked="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE) %>" id="enablePrivateLayoutsAutoCreate" inlineLabel="right" label="enable-private-layouts-auto-create" labelCssClass="simple-toggle-switch" name='<%= "settings--" + PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE + "--" %>' type="toggle-switch" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE) %>" />

		<aui:input checked="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) %>" id="enablePublicLayouts" inlineLabel="right" label="enable-public-layouts" labelCssClass="simple-toggle-switch" name='<%= "settings--" + PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED + "--" %>' onClick="togglePublicLayoutsAutoCreate()" type="toggle-switch" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) %>" />

		<aui:input checked="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE) %>" id="enablePublicLayoutsAutoCreate" inlineLabel="right" label="enable-public-layouts-auto-create" labelCssClass="simple-toggle-switch" name='<%= "settings--" + PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE + "--" %>' type="toggle-switch" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE) %>" />
	</div>
</div>

<script>
	let enablePrivateLayoutsElement = document.getElementById(
		'<portlet:namespace />enablePrivateLayouts'
	);

	let enablePrivateLayoutsAutoCreateElement = document.getElementById(
		'<portlet:namespace />enablePrivateLayoutsAutoCreate'
	);

	if (!enablePrivateLayoutsElement.checked) {
		enablePrivateLayoutsAutoCreateElement.setAttribute('disabled', '');
	}

	let enablePublicLayoutsElement = document.getElementById(
		'<portlet:namespace />enablePublicLayouts'
	);
	let enablePublicLayoutsAutoCreateElement = document.getElementById(
		'<portlet:namespace />enablePublicLayoutsAutoCreate'
	);

	if (!enablePublicLayoutsElement.checked) {
		enablePublicLayoutsAutoCreateElement.setAttribute('disabled', '');
	}

	function togglePrivateLayoutsAutoCreate() {
		if (!enablePrivateLayoutsElement.checked) {
			enablePrivateLayoutsAutoCreateElement.checked = false;
			enablePrivateLayoutsAutoCreateElement.setAttribute('disabled', '');
		}
		else {
			enablePrivateLayoutsAutoCreateElement.removeAttribute('disabled');
		}
	}

	function togglePublicLayoutsAutoCreate() {
		if (!enablePublicLayoutsElement.checked) {
			enablePublicLayoutsAutoCreateElement.checked = false;
			enablePublicLayoutsAutoCreateElement.setAttribute('disabled', '');
		}
		else {
			enablePublicLayoutsAutoCreateElement.removeAttribute('disabled');
		}
	}
</script>