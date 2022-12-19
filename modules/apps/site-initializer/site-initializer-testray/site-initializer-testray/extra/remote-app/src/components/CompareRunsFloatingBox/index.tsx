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
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {useEffect} from 'react';

import useRuns from '../../hooks/useRuns';
import i18n from '../../i18n';
import Form from '../Form';

type FloatingBoxProps = {
	expanded?: boolean;
	setVisible: (state: boolean) => void;
	visible: boolean;
};

const CompareRunsFloatingBox: React.FC<FloatingBoxProps> = ({
	expanded = false,
	setVisible,
	visible,
}) => {
	const {compareRuns, setRunA, setRunB} = useRuns();

	const validateButtonA = !(compareRuns.runId || compareRuns.runA);

	const validateButtonB = !(compareRuns.runId || compareRuns.runB);

	const validateCompareButtons = !(compareRuns.runA && compareRuns.runB);

	useEffect(() => {
		if (compareRuns.runA || compareRuns.runB) {
			setVisible(true);
		}
	}, [compareRuns, setVisible]);

	return (
		<div
			className={classNames('compare-runs-floating-box ', {
				'box-hidden': !visible && !expanded,
				'box-hidden-expanded': !visible && expanded,
				'box-visible': visible && !expanded,
				'box-visible-expanded': visible && expanded,
			})}
		>
			<div className="align-items d-flex flex-column justify-content-between m-3">
				<div className="align-items-center d-flex justify-content-between">
					<label className="mb-0">
						{i18n.translate('compare-runs')}
					</label>

					<span
						className="cursor-pointer"
						onClick={() => setVisible(false)}
					>
						<ClayIcon symbol="times" />
					</span>
				</div>

				<Form.Divider />

				<div className="mt-3">
					<ClayLayout.Row>
						<ClayLayout.Col>
							<ClayButton
								block
								disabled={validateButtonA}
								displayType="primary"
								onClick={() =>
									compareRuns.runId &&
									setRunA(compareRuns.runId)
								}
							>
								{compareRuns.runA
									? ` ${i18n.translate('run-a')} : ${
											compareRuns.runA
									  }`
									: i18n.translate('add-run-a')}
							</ClayButton>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayButton
								block
								disabled={validateButtonB}
								displayType="primary"
								onClick={() =>
									compareRuns.runId &&
									setRunB(compareRuns.runId)
								}
							>
								{compareRuns.runB
									? ` ${i18n.translate('run-b')} : ${
											compareRuns.runB
									  }`
									: i18n.translate('add-run-b')}
							</ClayButton>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<ClayLayout.Row className="mb-3 mt-4">
						<ClayLayout.Col className="d-flex justify-content-between">
							<ClayButton
								disabled={validateCompareButtons}
								displayType="primary"
							>
								{i18n.translate('compare-runs')}
							</ClayButton>

							<ClayButton
								disabled={validateCompareButtons}
								displayType="primary"
							>
								{i18n.sub('auto-fill-x', 'runs')}
							</ClayButton>

							<ClayButton
								disabled={validateCompareButtons}
								displayType="primary"
							>
								{i18n.sub('auto-fill-x', 'builds')}
							</ClayButton>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<div className="d-flex justify-content-end">
						<ClayButton
							displayType="secondary"
							onClick={() => {
								setRunA(null);
								setRunB(null);
							}}
						>
							{i18n.translate('clear')}
						</ClayButton>
					</div>
				</div>
			</div>
		</div>
	);
};

export default CompareRunsFloatingBox;
