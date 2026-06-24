/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayToolbar from '@clayui/toolbar';
import React, {useState} from 'react';

const NAME_MAX_LENGTH = 75;

interface IProps {
	backURL?: string;
	name?: string;
	namespace?: string;
}

export default function AudienceBuilder({
	backURL,
	name,
	namespace = '',
}: IProps) {
	const [currentName, setCurrentName] = useState(
		name || Liferay.Language.get('new-audience')
	);

	return (
		<>
			<ClayToolbar>
				<ClayLayout.ContainerFluid size={false}>
					<ClayToolbar.Nav>
						<ClayToolbar.Item>
							<ClayLink
								aria-label={Liferay.Language.get('back')}
								button
								displayType="unstyled"
								href={backURL}
								monospaced
							>
								<ClayIcon symbol="angle-left" />
							</ClayLink>
						</ClayToolbar.Item>

						<ClayToolbar.Item expand>
							<ClayToolbar.Section className="text-left">
								<span className="text-truncate">
									{currentName}
								</span>
							</ClayToolbar.Section>
						</ClayToolbar.Item>

						<ClayToolbar.Item>
							<ClayLink
								button
								displayType="secondary"
								href={backURL}
								small
							>
								{Liferay.Language.get('cancel')}
							</ClayLink>
						</ClayToolbar.Item>

						<ClayToolbar.Item>
							<ClayButton
								displayType="primary"
								size="sm"
								type="submit"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayToolbar.Item>
					</ClayToolbar.Nav>
				</ClayLayout.ContainerFluid>
			</ClayToolbar>

			<ClayLayout.Row className="m-0">
				<ClayLayout.Col className="border-right p-4" md={3}>
					<h4>{Liferay.Language.get('attributes-types')}</h4>
				</ClayLayout.Col>

				<ClayLayout.Col className="p-4" md={9}>
					<ClayForm.Group>
						<label htmlFor={`${namespace}name`}>
							{Liferay.Language.get('name')}

							<span className="reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<ClayInput
							id={`${namespace}name`}
							maxLength={NAME_MAX_LENGTH}
							name={`${namespace}name`}
							onChange={(event) =>
								setCurrentName(event.target.value)
							}
							required
							type="text"
							value={currentName}
						/>
					</ClayForm.Group>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</>
	);
}
