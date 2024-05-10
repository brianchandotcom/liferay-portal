import path from 'path';

import {WORK_EXPORT_PATH} from '../util/constants.mjs';
import getFlatName from '../util/getFlatName.mjs';

export default function getExportBridgePath(moduleName) {			
	return path.join(WORK_EXPORT_PATH, `${getFlatName(moduleName)}.js`);
}

