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

import React, {useContext, useState, useEffect, useRef} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {SplitPicker} from './SplitPicker/SplitPicker.es';
import {SliderWithLabel} from './SliderWithLabel.es';
import {SegmentsVariantType} from '../types.es';
import {
	INITIAL_CONFIDENCE_LEVEL,
	MAX_CONFIDENCE_LEVEL,
	MIN_CONFIDENCE_LEVEL,
	percentageNumberToIndex
} from '../util/percentages.es';
import BusyButton from './BusyButton/BusyButton.es';
import SegmentsExperimentContext from '../context.es';
import {StateContext} from '../state/context.es';
import {useDebounceCallback} from '../util/hooks.es';
import {SUCCESS_ANIMATION_FILE_NAME} from '../util/contants.es';

const TIME_ESTIMATION_THROTTLE_TIME_MS = 1000;

function ReviewExperimentModal({onRun, variants, visible, setVisible}) {
	const [busy, setBusy] = useState(false);
	const [success, setSuccess] = useState(false);
	const [estimation, setEstimation] = useState({
		days: null,
		loading: true
	});
	const [confidenceLevel, setConfidenceLevel] = useState(
		INITIAL_CONFIDENCE_LEVEL
	);
	const [draftVariants, setDraftVariants] = useState(
		variants.map((variant, index) => {
			const remainingSplit = 100 % variants.length;
			const splitValue = parseInt(100 / variants.length, 10);

			let split;

			if (index === 0 && remainingSplit > 0) {
				split = splitValue + remainingSplit;
			} else {
				split = splitValue;
			}

			return {...variant, split};
		})
	);
	const {assetsPath, APIService} = useContext(SegmentsExperimentContext);
	const {experiment} = useContext(StateContext);

	const {observer, onClose} = useModal({
		onClose: () => setVisible(false)
	});

	const mounted = useRef();

	useEffect(() => {
		mounted.current = true;
		return () => {
			mounted.current = false;
		};
	});

	const successAnimationPath = `${assetsPath}${SUCCESS_ANIMATION_FILE_NAME}`;

	const [getEstimation] = useDebounceCallback(body => {
		APIService.getEstimatedTime(body)
			.then(({segmentsExperimentEstimatedDaysDuration}) => {
				if (mounted.current) {
					setEstimation({
						days: segmentsExperimentEstimatedDaysDuration,
						loading: false
					});
				}
			})
			.catch(_error => {
				if (mounted.current) {
					setEstimation({
						days: null,
						loading: false
					});
				}
			});
	}, TIME_ESTIMATION_THROTTLE_TIME_MS);

	useEffect(() => {
		setEstimation({loading: true});

		getEstimation({
			confidenceLevel,
			segmentsExperimentId: experiment.segmentsExperimentId,
			segmentsExperimentRels: JSON.stringify(
				_variantsToSplitVariantsMap(draftVariants)
			)
		});
	}, [
		draftVariants,
		confidenceLevel,
		getEstimation,
		experiment.segmentsExperimentId
	]);

	return (
		visible && (
			<ClayModal observer={observer} size="lg">
				<ClayModal.Header>
					{success
						? Liferay.Language.get('test-started-successfully')
						: Liferay.Language.get('review-and-run-test')}
				</ClayModal.Header>
				<ClayModal.Body>
					{success ? (
						<div className="text-center">
							<img
								alt=""
								className="mb-4 mt-3"
								src={successAnimationPath}
								width="250px"
							/>
							<h3>
								{Liferay.Language.get('test-running-message')}
							</h3>
						</div>
					) : (
						<>
							<h3 className="sheet-subtitle border-bottom-0 text-secondary">
								{Liferay.Language.get('traffic-split')}
							</h3>

							<SplitPicker
								onChange={variants => {
									setDraftVariants(variants);
								}}
								variants={draftVariants}
							/>

							<hr />

							<h3 className="sheet-subtitle border-bottom-0 text-secondary">
								{Liferay.Language.get('confidence-level')}
							</h3>

							<SliderWithLabel
								label={Liferay.Language.get(
									'confidence-level-required'
								)}
								max={MAX_CONFIDENCE_LEVEL}
								min={MIN_CONFIDENCE_LEVEL}
								onValueChange={setConfidenceLevel}
								value={confidenceLevel}
							/>

							<hr />
							<div>
								<div className="d-flex">
									<label className="w-100">
										{Liferay.Language.get(
											'estimated-time-to-declare-winner'
										)}
									</label>

									<p className="mb-0 text-nowrap">
										{estimation.loading ? (
											<ClayLoadingIndicator
												className="my-0"
												small
											/>
										) : (
											estimation.days &&
											_getDaysMessage(estimation.days)
										)}
									</p>
								</div>
								<p className="small text-secondary">
									{Liferay.Language.get(
										'time-depends-on-confidence-level-and-traffic-to-the-variants'
									)}
								</p>
							</div>
						</>
					)}
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						success ? (
							<ClayButton.Group>
								<ClayButton
									displayType="primary"
									onClick={() => setVisible(false)}
								>
									{Liferay.Language.get('ok')}
								</ClayButton>
							</ClayButton.Group>
						) : (
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('Cancel')}
								</ClayButton>

								<BusyButton
									busy={busy}
									disabled={busy}
									onClick={_handleRun}
								>
									{Liferay.Language.get('Run')}
								</BusyButton>
							</ClayButton.Group>
						)
					}
				/>
			</ClayModal>
		)
	);

	/**
	 * Creates a splitVariantsMap `{ [segmentsExperimentRelId]: splitValue, ... }`
	 * and bubbles up `onRun` with `confidenceLevel` and `splitVariantsMap`
	 */
	function _handleRun() {
		const splitVariantsMap = _variantsToSplitVariantsMap(draftVariants);

		setBusy(true);

		onRun({
			confidenceLevel: percentageNumberToIndex(confidenceLevel),
			splitVariantsMap
		}).then(() => {
			if (mounted.current) {
				setBusy(false);
				setSuccess(true);
			}
		});
	}
}

function _variantsToSplitVariantsMap(variants) {
	return variants.reduce((acc, v) => {
		return {
			...acc,
			[v.segmentsExperimentRelId]: percentageNumberToIndex(v.split)
		};
	}, {});
}

function _getDaysMessage(days) {
	if (days === 1)
		return Liferay.Util.sub(Liferay.Language.get('x-day'), days);
	else return Liferay.Util.sub(Liferay.Language.get('x-days'), days);
}

ReviewExperimentModal.propTypes = {
	onRun: PropTypes.func.isRequired,
	setVisible: PropTypes.func.isRequired,
	variants: PropTypes.arrayOf(SegmentsVariantType),
	visible: PropTypes.bool.isRequired
};

export {ReviewExperimentModal};
