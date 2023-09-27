/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {UseFieldArrayAppend} from 'react-hook-form';

import i18n from '../../../../../../i18n';

type StackHistoryProps = {
	append: UseFieldArrayAppend<any>;
	fieldsHistory: any[];
	setFieldsHistory: (fieldHistory: any[]) => void;
};

const StackHistory: React.FC<StackHistoryProps> = ({
	append,
	fieldsHistory,
	setFieldsHistory,
}) => {
	const onClearHistory = () => {
		setFieldsHistory([]);
	};

	const onUndo = () => {
		const lastHistory = fieldsHistory[fieldsHistory.length - 1];

		setFieldsHistory(
			fieldsHistory.filter(
				(fieldHistory) => fieldHistory.id !== lastHistory.id
			)
		);

		append(lastHistory);
	};

	if (!fieldsHistory.length) {
		return null;
	}

	return (
		<ClayAlert displayType="info">
			<div className="d-flex justify-content-between">
				<span className="cursor-pointer" onClick={onUndo}>
					{i18n.sub('undo-x', fieldsHistory.length.toString())}
				</span>

				<span className="cursor-pointer" onClick={onClearHistory}>
					{i18n.translate('clear-history')}
				</span>
			</div>
		</ClayAlert>
	);
};

export default StackHistory;
