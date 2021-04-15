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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import getCN from 'classnames';
import React, {useState} from 'react';

import CodeMirrorEditor from '../shared/CodeMirrorEditor';
import PreviewModal from '../shared/PreviewModal';

const RESULTS_DEFAULT_KEYS = [
	'b_type',
	'b_summary',
	'b_created',
	'b_modified',
	'b_author',
	'b_assetEntryId',
];
const RESULTS_HIDE_KEYS = ['explanation', 'document', 'b_viewURL'];

function ResultListItem({item}) {
	const [collapse, setCollapse] = useState(true);

	const _renderListRow = (property) => (
		<ClayLayout.Row justify="start" key={`${item.id}_${property}`}>
			<ClayLayout.Col className="semibold" size={4}>
				{property}
			</ClayLayout.Col>

			<ClayLayout.Col
				className={getCN({'text-truncate': collapse})}
				size={8}
			>
				{item[property]}
			</ClayLayout.Col>
		</ClayLayout.Row>
	);

	return (
		<ClayList.Item className="result-list-item" flex key={item.b_title}>
			<ClayList.ItemField>
				<PreviewModal
					body={
						<div className="json-modal">
							<CodeMirrorEditor
								readOnly
								value={item.explanation}
							/>
						</div>
					}
					size="lg"
					title={Liferay.Language.get('score-explanation')}
				>
					<ClayButton className="score" displayType="unstyled" small>
						{item.score.toFixed(2)}
					</ClayButton>
				</PreviewModal>
			</ClayList.ItemField>

			<ClayList.ItemField expand>
				<ClayList.ItemTitle>
					<ClayLink href={item.b_viewURL} target="_blank">
						{item.b_title}
						<ClayIcon className="shortcut-icon" symbol="shortcut" />
					</ClayLink>
				</ClayList.ItemTitle>

				{RESULTS_DEFAULT_KEYS.map((property) =>
					_renderListRow(property)
				)}

				{!collapse &&
					Object.keys(item)
						.sort()
						.map((property) => {
							if (
								![
									...RESULTS_DEFAULT_KEYS,
									...RESULTS_HIDE_KEYS,
								].includes(property)
							) {
								return _renderListRow(property);
							}
						})}
			</ClayList.ItemField>

			<ClayList.ItemField>
				<ClayButton
					aria-label={
						collapse
							? Liferay.Language.get('expand')
							: Liferay.Language.get('collapse')
					}
					displayType="unstyled"
					onClick={() => setCollapse(!collapse)}
				>
					<ClayIcon
						symbol={collapse ? 'angle-right' : 'angle-down'}
					/>
				</ClayButton>
			</ClayList.ItemField>
		</ClayList.Item>
	);
}

export default ResultListItem;
