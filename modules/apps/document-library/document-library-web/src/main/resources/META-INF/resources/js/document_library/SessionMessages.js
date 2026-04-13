/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

function SessionMessages({fileEntry}) {
	if (fileEntry) {
		Liferay.fire('fileEntrySaved', {
			fileEntryId: fileEntry.fileEntryId,
			fileName: fileEntry.fileName,
			groupId: fileEntry.groupId,
		});
	}

	return;
}

SessionMessages.propTypes = {
	fileEntry: PropTypes.shape({
		fileEntryId: PropTypes.number,
		fileName: PropTypes.string,
		groupId: PropTypes.string,
	}),
};

export default SessionMessages;
