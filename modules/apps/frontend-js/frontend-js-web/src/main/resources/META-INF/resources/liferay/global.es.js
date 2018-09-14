import {core} from 'metal';
import portlet from './portlet/portlet.es';
import navigate from './util/navigate.es';
import objectToFormData from './util/object_to_form_data.es';

Liferay.Util.isFunction = core.isFunction;
Liferay.Util.navigate = navigate;
Liferay.Util.objectToFormData = objectToFormData;

export {portlet};