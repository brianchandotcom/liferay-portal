/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayUpperToolbar from '@clayui/upper-toolbar';
import React, {useState} from 'react';

export default function ({title, version}) {
	const [definitionTitle, setDefinitionTitle] = useState(title);

	return (
		<ClayUpperToolbar className="upper-toolbar-component">
			<ClayUpperToolbar.Item>
				<ClayButton
					className="translation-button"
					displayType="secondary"
				>
					<ClayIcon symbol="en-us" />
					<div className="translation-label">en-US</div>
				</ClayButton>
			</ClayUpperToolbar.Item>
			<ClayUpperToolbar.Item expand>
				<ClayUpperToolbar.Input
					id="definition-title"
					onChange={({target: {value}}) => {
						setDefinitionTitle(value);
					}}
					placeholder={Liferay.Language.get('untitled-workflow')}
					type="text"
					value={definitionTitle}
				/>
			</ClayUpperToolbar.Item>
			<ClayUpperToolbar.Item>
				<ClayLabel className="version" displayType="secondary">
					<div>
						{Liferay.Language.get('version')}:
						<span className="version-text">{version}</span>
					</div>
				</ClayLabel>
			</ClayUpperToolbar.Item>
			<ClayUpperToolbar.Item>
				<ClayButton displayType="secondary">
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayUpperToolbar.Item>
			<ClayUpperToolbar.Item>
				<ClayButton displayType="secondary">
					{Liferay.Language.get('save')}
				</ClayButton>
			</ClayUpperToolbar.Item>
			<ClayUpperToolbar.Item>
				<ClayButton displayType="primary">
					{Liferay.Language.get('publish')}
				</ClayButton>
			</ClayUpperToolbar.Item>
			<ClayUpperToolbar.Item>
				<ClayButtonWithIcon
					displayType="secondary"
					onClick={() => {}}
					symbol="code"
				/>
			</ClayUpperToolbar.Item>
		</ClayUpperToolbar>
	);
}
