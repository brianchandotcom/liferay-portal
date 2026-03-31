/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {Link} from '@clayui/toolbar/src/Link';
import React from 'react';

import Toolbar from '../components/ToolBar';
import InlineTextInput from './inline_text_input/InlineTextInput';

import './ContentRetriever.scss';

import Icon from '@clayui/icon';

export default function ContentRetrieverForm({backURL}: {backURL: string}) {
	return (
		<>
			<Toolbar backURL={backURL} title={Liferay.Language.get('add-url')}>
				<Toolbar.Item>
					<Link
						aria-label={Liferay.Language.get('cancel')}
						borderless
						button
						displayType="secondary"
						href={backURL}
						small
					>
						{Liferay.Language.get('cancel')}
					</Link>
				</Toolbar.Item>

				<Toolbar.Item>
					<Button
						aria-labelledby="saveButton"
						data-title="Save Button"
						data-title-set-as-html
						disabled={false}
						onClick={() => {}}
						size="sm"
					>
						{Liferay.Language.get('save')}
					</Button>
				</Toolbar.Item>
			</Toolbar>
			<ClayLayout.ContainerFluid className="content-retriever-container mt-5">
				<ClayForm>
					<InlineTextInput
						name="name"
						placeholder={Liferay.Language.get('add-a-name')}
					/>

					<div className="align-items-center content-retriever-url-field-container mb-4 mt-4">
						<div className="content-retriever-url-field-label">
							<span className="mr-3">
								<Icon symbol="link" />
							</span>

							<label htmlFor="data-source-url">
								{Liferay.Language.get('external-website')}
							</label>
						</div>

						<ClayInput
							id="data-source-url"
							placeholder={Liferay.Language.get('add-a-url-here')}
							type="text"
						/>
					</div>

					<label htmlFor="data-source-description">
						{Liferay.Language.get('description')}

						<span className="ml-1 reference-mark text-warning">
							<Icon symbol="asterisk" />
						</span>
					</label>

					<ClayInput
						component="textarea"
						id="data-source-description"
						placeholder={Liferay.Language.get('add-a-description')}
						type="text"
					/>
				</ClayForm>
			</ClayLayout.ContainerFluid>
		</>
	);
}
