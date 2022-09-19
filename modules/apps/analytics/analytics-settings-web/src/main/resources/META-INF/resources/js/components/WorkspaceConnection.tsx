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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';

import BasePage from './BasePage';

// import { Container } from './styles';

const WorkspaceConnection: React.FC = () => {
	return (
		<BasePage
			description={Liferay.Language.get(
				'use-the-token-genereted-in-your-analytics-cloud-to-connect-this-workspace'
			)}
			title={Liferay.Language.get('workspace-connection')}
		>
			<ClayForm>
				<ClayForm.Group>
					<label className="disabled" htmlFor="inputToken">
						{Liferay.Language.get('analytics-cloud-token')}
					</label>

					<ClayInput
						disabled
						id="inputToken"
						type="text"
						value="banana"
					/>

					<small className="disabled text-secondary">
						{Liferay.Language.get('analytics-cloud-token-help')}
					</small>
				</ClayForm.Group>
			</ClayForm>

			<ClayButton
				className="mr-3"
				displayType="secondary"
				onClick={() => alert('Vai pra o workspace4')}
			>
				{Liferay.Language.get('go-to-workspace')}

				<ClayIcon className="ml-2" symbol="shortcut" />
			</ClayButton>

			<ClayButton
				displayType="secondary"
				onClick={() =>
					alert('Desconecta hauhauahauhauhuusausuasuhaush')
				}
			>
				{Liferay.Language.get('disconnect')}
			</ClayButton>
		</BasePage>
	);
};

export default WorkspaceConnection;
