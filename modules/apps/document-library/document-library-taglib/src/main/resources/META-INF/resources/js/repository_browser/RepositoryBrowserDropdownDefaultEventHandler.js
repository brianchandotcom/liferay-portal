/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler} from 'frontend-js-web';

import {deleteEntry, renameEntry} from './RepositoryBrowserDropdownActions';

class ElementsDefaultEventHandler extends DefaultEventHandler {
	delete({deleteURL}) {
		deleteEntry(deleteURL);
	}

	rename({renameURL, value}) {
		renameEntry(renameURL, value);
	}
}

export default ElementsDefaultEventHandler;
