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
import React from 'react';

import Timeline from './Timeline.es';

export default function RuleEditor({onCancel, onSubmit}) {
	return (
		<Timeline>
			<div className="liferay-ddm-form-rule-builder-header">
				<h2 className="form-builder-section-title text-default">
					{Liferay.Language.get('rule')}
				</h2>

				<h4 className="text-default">
					{Liferay.Language.get(
						'define-condition-and-action-to-change-fields-and-elements-on-the-form'
					)}
				</h4>
			</div>
			<Timeline.List>
				<Timeline.Header title={Liferay.Language.get('condition')} />
				<Timeline.Item>
					<Timeline.Condition expression={Liferay.Language.get('if')}>
						<input />
					</Timeline.Condition>
					<Timeline.ActionTrash />
				</Timeline.Item>
				<Timeline.Item>
					<Timeline.IncrementButton />
				</Timeline.Item>
			</Timeline.List>
			<Timeline.List>
				<Timeline.Header title={Liferay.Language.get('actions')} />
				<Timeline.Item>
					<Timeline.Condition expression={Liferay.Language.get('do')}>
						<input />
					</Timeline.Condition>
					<Timeline.ActionTrash />
				</Timeline.Item>
				<Timeline.Item>
					<Timeline.IncrementButton />
				</Timeline.Item>
			</Timeline.List>
			<div className="liferay-ddm-form-rule-builder-footer">
				<ClayButton.Group spaced>
					<ClayButton displayType="primary" onClick={onSubmit}>
						{Liferay.Language.get('save')}
					</ClayButton>
					<ClayButton displayType="secondary" onClick={onCancel}>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</Timeline>
	);
}
