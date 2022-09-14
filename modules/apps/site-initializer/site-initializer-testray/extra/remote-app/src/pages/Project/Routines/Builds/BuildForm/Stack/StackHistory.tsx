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
