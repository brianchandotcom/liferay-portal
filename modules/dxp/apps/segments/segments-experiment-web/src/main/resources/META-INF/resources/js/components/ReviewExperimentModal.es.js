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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState
} from 'react';

import SegmentsExperimentContext from '../context.es';
import {StateContext} from '../state/context.es';
import {SegmentsVariantType} from '../types.es';
import {SUCCESS_ANIMATION_FILE_NAME} from '../util/contants.es';
import {useDebounceCallback} from '../util/hooks.es';
import {
	INITIAL_CONFIDENCE_LEVEL,
	MAX_CONFIDENCE_LEVEL,
	MIN_CONFIDENCE_LEVEL,
	percentageNumberToIndex
} from '../util/percentages.es';
import BusyButton from './BusyButton/BusyButton.es';
import {SliderWithLabel} from './SliderWithLabel.es';
import {SplitPicker} from './SplitPicker/SplitPicker.es';

const TIME_ESTIMATION_THROTTLE_TIME_MS = 1000;

function ReviewExperimentModal({modalObserver, onModalClose, onRun, variants}) {
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
			}
			else {
				split = splitValue;
			}

			return {...variant, split};
		})
	);
	const {APIService, assetsPath} = useContext(SegmentsExperimentContext);
	const {experiment} = useContext(StateContext);

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
						error: true
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

	const [height, setHeight] = useState(0);

	const measureHeight = useCallback(
		node => {
			if (node !== null && !success) {
				setHeight(node.getBoundingClientRect().height);
			}
		},
		[setHeight, success]
	);

	return (
		<ClayModal observer={modalObserver} size="lg">
			<ClayModal.Header>
				{success
					? Liferay.Language.get('test-started-successfully')
					: Liferay.Language.get('review-and-run-test')}
			</ClayModal.Header>
			<ClayModal.Body>
				{success ? (
					<div
						className="text-center"
						style={{height: height + 'px'}}
					>
						<img
							alt=""
							className="mb-4 mt-3"
							src={successAnimationPath}
							width="250px"
						/>
						<h3>{Liferay.Language.get('test-running-message')}</h3>
					</div>
				) : (
					<div ref={measureHeight}>
						<h3 className="border-bottom-0 sheet-subtitle text-secondary">
							{Liferay.Language.get('traffic-split')}
						</h3>

						<SplitPicker
							onChange={variants => {
								setDraftVariants(variants);
							}}
							variants={draftVariants}
						/>

						<hr />

						<h3 className="border-bottom-0 sheet-subtitle text-secondary">
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
						<div className="d-flex">
							<div className="w-100">
								<label>
									{Liferay.Language.get(
										'estimated-time-to-declare-winner'
									)}
								</label>

								<p className="small text-secondary">
									{Liferay.Language.get(
										'time-depends-on-confidence-level-and-traffic-to-the-variants'
									)}
								</p>
							</div>

							<p className="mb-0 text-nowrap">
								{estimation.loading && (
									<ClayLoadingIndicator
										className="my-0"
										small
									/>
								)}

								{!estimation.loading &&
								(estimation.days === undefined ||
									estimation.error) ? (
									<span className="small text-secondary">
										{Liferay.Language.get('not-available')}
									</span>
								) : (
									estimation.days &&
									_getDaysMessage(estimation.days)
								)}
							</p>
						</div>
					</div>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					success ? (
						<ClayButton.Group>
							<ClayButton
								displayType="primary"
								onClick={onModalClose}
							>
								{Liferay.Language.get('ok')}
							</ClayButton>
						</ClayButton.Group>
					) : (
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onModalClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<BusyButton
								busy={busy}
								disabled={busy}
								onClick={_handleRun}
							>
								{Liferay.Language.get('run')}
							</BusyButton>
						</ClayButton.Group>
					)
				}
			/>
		</ClayModal>
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
	if (days === 1) {
		return Liferay.Util.sub(Liferay.Language.get('x-day'), days);
	}
	else {
		return Liferay.Util.sub(Liferay.Language.get('x-days'), days);
	}
}

ReviewExperimentModal.propTypes = {
	modalObserver: PropTypes.object.isRequired,
	onModalClose: PropTypes.func.isRequired,
	onRun: PropTypes.func.isRequired,
	variants: PropTypes.arrayOf(SegmentsVariantType)
};

export {ReviewExperimentModal};
