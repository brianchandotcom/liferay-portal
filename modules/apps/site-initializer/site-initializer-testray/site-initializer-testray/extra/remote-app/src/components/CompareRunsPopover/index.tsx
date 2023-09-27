/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {useEffect, useRef} from 'react';
import {useNavigate} from 'react-router-dom';
import {useObjectPermission} from '~/hooks/data/useObjectPermission';

import useRuns from '../../hooks/useRuns';
import i18n from '../../i18n';
import {Liferay} from '../../services/liferay';
import {testrayRunImpl} from '../../services/rest';
import Form from '../Form';

type CompareRunsPopoverProps = {
	expanded?: boolean;
	setVisible: (state: boolean) => void;
	triggedRef: React.RefObject<HTMLDivElement>;
	visible: boolean;
};

const CompareRunsPopover: React.FC<CompareRunsPopoverProps> = ({
	expanded = false,
	setVisible,
	triggedRef,
	visible,
}) => {
	const ref = useRef<HTMLDivElement>(null);

	const {compareRuns, setRunA, setRunB} = useRuns();
	const disableButtonA = !(compareRuns?.runId || compareRuns?.runA);
	const disableButtonB = !(compareRuns?.runId || compareRuns?.runB);
	const caseResultPermission = useObjectPermission('/caseresults');
	const disbleButtonAutofillRuns = caseResultPermission.canCreate;
	const buildsPermission = useObjectPermission('/builds');
	const disbleButtonAutofillBuilds = buildsPermission.canCreate;

	const validateCompareButtons = !(compareRuns?.runA && compareRuns?.runB);
	const navigate = useNavigate();

	const onAutoFill = (type: 'Build' | 'Run') => {
		if (!compareRuns.runA || !compareRuns.runB) {
			return;
		}

		testrayRunImpl
			.autofill(compareRuns.runA, compareRuns.runB, type)
			.then(() =>
				Liferay.Util.openToast({
					message: i18n.sub(
						'auto-fill-x-is-scheduled-to-be-processed',
						type
					),
				})
			)
			.then(() => setVisible(false))
			.catch(() =>
				Liferay.Util.openToast({
					message: i18n.translate('an-unexpected-error-occurred'),
					type: 'danger',
				})
			);
	};

	useEffect(() => {
		const handleClickOutside = (event: any) => {
			if (
				ref.current &&
				!ref.current.contains(event.target) &&
				!triggedRef.current?.contains(event.target)
			) {
				setVisible(false);
			}
		};

		document.addEventListener('mousedown', handleClickOutside);

		return () =>
			document.removeEventListener('mousedown', handleClickOutside);
	}, [setVisible, triggedRef]);

	return (
		<div
			className={classNames('tr-compare-runs-popover', {
				'hidden': !visible && !expanded,
				'hidden--expanded': !visible && expanded,
				'visible': visible && !expanded,
				'visible--expanded': visible && expanded,
			})}
			onBlur={() => setVisible(false)}
			ref={ref}
		>
			<div className="align-items d-flex flex-column justify-content-between m-3">
				<div className="align-items-center d-flex justify-content-between">
					<label className="mb-0">
						{i18n.sub('compare-x', 'runs')}
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
								className="text-uppercase"
								disabled={disableButtonA}
								displayType="primary"
								onClick={() => {
									if (compareRuns?.runId) {
										setRunA(compareRuns?.runId);
									}
								}}
							>
								{compareRuns?.runA
									? `${i18n.translate('run-a')} : ${
											compareRuns?.runA
									  }`
									: i18n.translate('add-run-a')}
							</ClayButton>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayButton
								block
								className="text-uppercase"
								disabled={disableButtonB}
								displayType="primary"
								onClick={() => {
									if (compareRuns?.runId) {
										setRunB(compareRuns?.runId);
									}
								}}
							>
								{compareRuns?.runB
									? `${i18n.translate('run-b')} : ${
											compareRuns?.runB
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
								onClick={() => {
									navigate(
										`/compare-runs/${compareRuns.runA}/${compareRuns.runB}/teams`
									);

									setVisible(false);
								}}
							>
								{i18n.sub('compare-x', 'runs')}
							</ClayButton>

							<ClayButton
								disabled={
									validateCompareButtons ||
									!disbleButtonAutofillRuns
								}
								displayType="primary"
								onClick={() => onAutoFill('Run')}
							>
								{i18n.sub('auto-fill-x', 'runs')}
							</ClayButton>

							<ClayButton
								disabled={
									validateCompareButtons ||
									!disbleButtonAutofillBuilds
								}
								displayType="primary"
								onClick={() => onAutoFill('Build')}
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

export default CompareRunsPopover;
