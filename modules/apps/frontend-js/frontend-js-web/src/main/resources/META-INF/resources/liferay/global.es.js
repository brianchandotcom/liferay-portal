import {
	component,
	componentReady,
	destroyComponent,
	destroyComponents,
	destroyUnfulfilledPromises,
	getComponentCache,
	initComponentCache
} from './component.es';
import escape from 'lodash.escape';
import groupBy from 'lodash.groupby';
import isEqual from 'lodash.isequal';
import portlet from './portlet/portlet.es';
import navigate from './util/navigate.es';
import ns from './util/ns.es';
import objectToFormData from './util/object_to_form_data.es';
import toCharCode from './util/to_char_code.es';
import unescape from 'lodash.unescape';

Liferay.component = component;
Liferay.componentReady = componentReady;
Liferay.destroyComponent = destroyComponent;
Liferay.destroyComponents = destroyComponents;
Liferay.destroyUnfulfilledPromises = destroyUnfulfilledPromises;
Liferay.getComponentCache = getComponentCache;
Liferay.initComponentCache = initComponentCache;

Liferay.Util.escape = escape;
Liferay.Util.groupBy = groupBy;
Liferay.Util.isEqual = isEqual;
Liferay.Util.navigate = navigate;
Liferay.Util.ns = ns;
Liferay.Util.objectToFormData = objectToFormData;
Liferay.Util.toCharCode = toCharCode;

Liferay.Util.openToast = (...args) => {
	Liferay.Loader.require(
		'frontend-js-web/liferay/toast/commands/OpenToast.es',
		commands => {
			commands.openToast(...args);
		}
	);
};

Liferay.Util.unescape = unescape;

export {portlet};