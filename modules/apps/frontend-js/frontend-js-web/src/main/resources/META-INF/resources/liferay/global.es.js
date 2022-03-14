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

import escape from 'lodash.escape';
import groupBy from 'lodash.groupby';
import isEqual from 'lodash.isequal';
import unescape from 'lodash.unescape';

import DynamicSelect from './DynamicSelect';
import BREAKPOINTS from './breakpoints';
import {
	component,
	componentReady,
	destroyComponent,
	destroyComponents,
	destroyUnfulfilledPromises,
	getComponentCache,
	initComponentCache,
} from './component.es';
import {
	getLayoutIcons,
	hideLayoutPane,
	proposeLayout,
	publishToLive,
	showLayoutPane,
	toggleLayoutDetails,
} from './layout_exporter.es';
import {showTab} from './portal/tabs.es';
import {showTooltip} from './portal/tooltip.es';
import portlet, {minimizePortlet} from './portlet/portlet.es';
import SideNavigation from './side_navigation.es';
import addParams from './util/add_params';
import getCountries from './util/address/get_countries.es';
import getRegions from './util/address/get_regions.es';
import fetch from './util/fetch.es';
import focusFormField from './util/focus_form_field';
import getFormElement from './util/form/get_form_element.es';
import objectToFormData from './util/form/object_to_form_data.es';
import postForm from './util/form/post_form.es';
import setFormValues from './util/form/set_form_values.es';
import formatStorage from './util/format_storage.es';
import formatXML from './util/format_xml.es';
import getCropRegion from './util/get_crop_region.es';
import getDOM from './util/get_dom';
import getElement from './util/get_element';
import getLexiconIcon from './util/get_lexicon_icon';
import getPortletId from './util/get_portlet_id';
import getPortletNamespace from './util/get_portlet_namespace.es';
import {
	MAP_HTML_CHARS_ESCAPED,
	escapeHTML,
	unescapeHTML,
} from './util/html_util';
import inBrowserView from './util/in_browser_view';
import isPhone from './util/is_phone';
import isTablet from './util/is_tablet';
import navigate from './util/navigate.es';
import normalizeFriendlyURL from './util/normalize_friendly_url';
import ns from './util/ns.es';
import objectToURLSearchParams from './util/object_to_url_search_params.es';
import createActionURL from './util/portlet_url/create_action_url.es';
import createPortletURL from './util/portlet_url/create_portlet_url.es';
import createRenderURL from './util/portlet_url/create_render_url.es';
import createResourceURL from './util/portlet_url/create_resource_url.es';
import {getSessionValue, setSessionValue} from './util/session.es';
import toCharCode from './util/to_char_code.es';
import toggleDisabled from './util/toggle_disabled';

Liferay = window.Liferay || {};

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {BREAKPOINTS} from 'frontend-js-web'`
 */
Liferay.BREAKPOINTS = BREAKPOINTS;

Liferay.component = component;
Liferay.componentReady = componentReady;
Liferay.destroyComponent = destroyComponent;
Liferay.destroyComponents = destroyComponents;
Liferay.destroyUnfulfilledPromises = destroyUnfulfilledPromises;
Liferay.getComponentCache = getComponentCache;
Liferay.initComponentCache = initComponentCache;

Liferay.Address = {
	getCountries,
	getRegions,
};

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
Liferay.DynamicSelect = DynamicSelect;

Liferay.LayoutExporter = {
	all: hideLayoutPane,
	details: toggleLayoutDetails,
	icons: getLayoutIcons(),
	proposeLayout,
	publishToLive,
	selected: showLayoutPane,
};

Liferay.Portal = {
	Tabs: {
		show: showTab,
	},
	ToolTip: {
		show: showTooltip,
	},
};

Liferay.Portlet = Liferay.Portlet || {};

Liferay.Portlet.minimize = minimizePortlet;

Liferay.Portlet.openModal = (...args) => {
	Liferay.Loader.require(
		'frontend-js-web/liferay/modal/Modal',
		(commands) => {
			commands.openPortletModal(...args);
		}
	);
};

Liferay.Portlet.openWindow = (...args) => {
	Liferay.Loader.require(
		'frontend-js-web/liferay/modal/Modal',
		(commands) => {
			commands.openPortletWindow(...args);
		}
	);
};

Liferay.SideNavigation = SideNavigation;

Liferay.Util = Liferay.Util || {};

Liferay.Util.MAP_HTML_CHARS_ESCAPED = MAP_HTML_CHARS_ESCAPED;

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {addParams} from 'frontend-js-web'`
 */
Liferay.Util.addParams = addParams;

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
Liferay.Util.disableEsc = () => {
	if (document.all && window.event.keyCode === 27) {
		window.event.returnValue = false;
	}
};

Liferay.Util.escape = escape;
Liferay.Util.escapeHTML = escapeHTML;
Liferay.Util.fetch = fetch;

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {focusFormField} from 'frontend-js-web'`
 */
Liferay.Util.focusFormField = focusFormField;

Liferay.Util.formatStorage = formatStorage;
Liferay.Util.formatXML = formatXML;
Liferay.Util.getCropRegion = getCropRegion;

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
Liferay.Util.getDOM = getDOM;

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
Liferay.Util.getElement = getElement;

Liferay.Util.getFormElement = getFormElement;
Liferay.Util.getLexiconIcon = getLexiconIcon;

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {getPortletId} from 'frontend-js-web'`
 */
Liferay.Util.getPortletId = getPortletId;

Liferay.Util.getPortletNamespace = getPortletNamespace;
Liferay.Util.groupBy = groupBy;

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {inBrowserView} from 'frontend-js-web'`
 */
Liferay.Util.inBrowserView = inBrowserView;

Liferay.Util.isEqual = isEqual;

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {isPhone} from 'frontend-js-web'`
 */
Liferay.Util.isPhone = isPhone;

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {isTablet} from 'frontend-js-web'`
 */
Liferay.Util.isTablet = isTablet;

Liferay.Util.navigate = navigate;
Liferay.Util.ns = ns;
Liferay.Util.objectToFormData = objectToFormData;
Liferay.Util.objectToURLSearchParams = objectToURLSearchParams;

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {normalizeFriendlyURL} from 'frontend-js-web'`
 */
Liferay.Util.normalizeFriendlyURL = normalizeFriendlyURL;

Liferay.Util.PortletURL = {
	createActionURL,
	createPortletURL,
	createRenderURL,
	createResourceURL,
};

Liferay.Util.postForm = postForm;
Liferay.Util.setFormValues = setFormValues;
Liferay.Util.toCharCode = toCharCode;

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `import {toggleDisabled} from 'frontend-js-web'`
 */
Liferay.Util.toggleDisabled = toggleDisabled;

Liferay.Util.openModal = (...args) => {
	Liferay.Loader.require(
		'frontend-js-web/liferay/modal/Modal',
		(commands) => {
			commands.openModal(...args);
		}
	);
};

Liferay.Util.openSelectionModal = (...args) => {
	Liferay.Loader.require(
		'frontend-js-web/liferay/modal/Modal',
		(commands) => {
			commands.openSelectionModal(...args);
		}
	);
};

Liferay.Util.openToast = (...args) => {
	Liferay.Loader.require(
		'frontend-js-web/liferay/toast/commands/OpenToast.es',
		(commands) => {
			commands.openToast(...args);
		}
	);
};

Liferay.Util.Session = {
	get: getSessionValue,
	set: setSessionValue,
};

Liferay.Util.unescape = unescape;
Liferay.Util.unescapeHTML = unescapeHTML;

export {portlet};
