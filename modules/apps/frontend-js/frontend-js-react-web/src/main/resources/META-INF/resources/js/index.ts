/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import process from 'process';

export {default as ReactDOMServer} from 'react-dom/server';

export {default as render} from './render';
export {default as ReactPortal} from './ReactPortal';
export {default as useEventListener} from './hooks/useEventListener';
export {default as useInterval} from './hooks/useInterval';
export {default as useIsMounted} from './hooks/useIsMounted';
export {default as usePrevious} from './hooks/usePrevious';
export {default as useStateSafe} from './hooks/useStateSafe';
export {default as useThunk} from './hooks/useThunk';
export {default as useTimeout} from './hooks/useTimeout';

// Egregious hack because react-dnd expects `window.process` to exist:
//
// https://github.com/react-dnd/asap/blob/b6bebeb734/src/node/asap.ts#L24

if (!window.process) {
	window.process = process;
}
