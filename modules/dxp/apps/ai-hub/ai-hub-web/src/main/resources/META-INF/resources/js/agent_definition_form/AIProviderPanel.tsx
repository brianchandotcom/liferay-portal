/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Icon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import Link from '@clayui/link';
import ClayPanel from '@clayui/panel';
import React from 'react';

import {Model} from './types/AgentDefinition';

interface IProps {
	model?: Model;
}

const AIProviderPanel: React.FC<IProps> = ({model}) => {
	const modelLabel =
		model?.label || Liferay.Language.get('no-ai-model-defined');
	const providerLabel =
		model?.providerLabel || Liferay.Language.get('no-ai-provider-defined');

	return (
		<ClayPanel
			className="agent-definition-form-ai-provider"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="agent-definition-form-header">
					<h2>{Liferay.Language.get('ai-provider')}</h2>
				</div>

				<dl className="ai-provider-section__details mb-0">
					<ClayLayout.Row className="ai-provider-section__details-item">
						<dt className="col-4">
							{Liferay.Language.get('provider')}
						</dt>

						<dd className="col-8">{providerLabel}</dd>
					</ClayLayout.Row>

					<ClayLayout.Row className="ai-provider-section__details-item">
						<dt className="col-4">
							{Liferay.Language.get('model')}
						</dt>

						<dd className="col-8">{modelLabel}</dd>
					</ClayLayout.Row>

					<ClayLayout.Row className="ai-provider-section__details-item">
						<dt className="col-4">
							{Liferay.Language.get('risk-classification')}
						</dt>

						<dd className="col-8">
							<ClayLabel displayType="success" inverse large>
								{Liferay.Language.get('minimum-risk')}
							</ClayLabel>
						</dd>
					</ClayLayout.Row>

					<ClayLayout.Row className="ai-provider-section__details-item">
						<dt className="col-4">
							{Liferay.Language.get('compliance-docs')}
						</dt>

						<dd className="col-8">
							<Link href="#" target="_blank">
								{Liferay.Language.get(
									'vendor-compliance-documentation'
								)}

								<Icon className="ml-2" symbol="shortcut" />
							</Link>
						</dd>
					</ClayLayout.Row>
				</dl>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default AIProviderPanel;
