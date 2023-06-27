import Alert, {AlertTypes} from 'shared/components/Alert';
import BaseModal from 'experiments/components/modals/BaseModal';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import React, {useEffect, useState} from 'react';
import {EXPERIMENT_ESTIMATED_DAYS_DURATION} from 'experiments/queries/ExperimentQuery';
import {EXPERIMENT_MUTATION} from 'experiments/queries/ExperimentMutation';
import {makeAllRefetch} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {toRounded} from 'shared/util/numbers';
import {useDebounce} from 'shared/hooks';
import {useMutation, useQuery} from '@apollo/react-hooks';
import {useStateValue} from 'experiments/state';

const CONFIDENCE_LEVEL = 95;
const DEBOUNCE = 400;
const MAX_CONFIDENCE_LEVEL = 99;
const MAX_TRAFFIC_SPLIT = 99;
const MIN_CONFIDENCE_LEVEL = 80;
const MIN_TRAFFIC_SPLIT = 1;
const TRAFFIC_SPLIT_TOTAL = 100;

const initialStateVariants = variants => {
	if (!variants) return [];

	const trafficSplit = parseInt(toRounded(100 / variants.length));
	const trafficSplitControl =
		100 - trafficSplit * variants.length + trafficSplit;

	return variants.map((variant, index) => ({
		...variant,
		trafficSplit: index === 0 ? trafficSplitControl : trafficSplit
	}));
};

const getDxpVariantsSettings = variants =>
	variants.map(({control, dxpVariantId, trafficSplit}) => ({
		control,
		dxpVariantId,
		trafficSplit
	}));

const RunExperimentModal = ({dxpVariants, experimentId, observer, onClose}) => {
	const [{allRefetch}]: any = useStateValue();
	const [mutate] = useMutation(EXPERIMENT_MUTATION);
	const [variants, setVariants] = useState(initialStateVariants(dxpVariants));
	const [trafficSplitTotal, setTrafficSplitTotal] = useState(
		TRAFFIC_SPLIT_TOTAL
	);
	const [disabled, setDisabled] = useState(false);
	const [confidenceLevel, setConfidenceLevel] = useState(CONFIDENCE_LEVEL);
	const [
		confidenceLevelWithDebounce,
		setConfidenceLevelWithDebounce
	] = useState(confidenceLevel);

	const confidenceLeveInRange =
		confidenceLevel >= MIN_CONFIDENCE_LEVEL &&
		confidenceLevel <= MAX_CONFIDENCE_LEVEL;

	const debouncedConfidenceLevel = useDebounce(confidenceLevel, DEBOUNCE);

	useEffect(() => {
		if (confidenceLeveInRange) {
			setConfidenceLevelWithDebounce(debouncedConfidenceLevel);
		}
	}, [debouncedConfidenceLevel]);

	useEffect(() => {
		setTrafficSplitTotal(
			variants.reduce((prev, next) => prev + next.trafficSplit, 0)
		);
	}, [variants]);

	useEffect(() => {
		setDisabled(trafficSplitTotal !== 100 || !confidenceLeveInRange);
	}, [confidenceLeveInRange, trafficSplitTotal]);

	const {
		data: estimatedDaysData,
		error: estimatedDaysError,
		loading: estimatedDaysLoading
	} = useQuery(EXPERIMENT_ESTIMATED_DAYS_DURATION, {
		skip: trafficSplitTotal !== 100 || !confidenceLeveInRange,
		variables: {
			experimentId,
			experimentSettings: {
				confidenceLevel: confidenceLevelWithDebounce,
				dxpVariantsSettings: getDxpVariantsSettings(variants)
			}
		}
	});

	const onSubmit = () =>
		mutate({
			variables: {
				experimentId,
				experimentSettings: {
					confidenceLevel,
					dxpVariantsSettings: getDxpVariantsSettings(variants)
				},
				status: 'RUNNING'
			}
		});

	return (
		<BaseModal
			disabled={disabled}
			observer={observer}
			onClose={onClose}
			onSubmit={onSubmit}
			onSuccess={() => makeAllRefetch(allRefetch)}
			submitMessage={Liferay.Language.get('run-test')}
			title={Liferay.Language.get('review-&-run')}
		>
			{estimatedDaysError && (
				<Alert
					title={String(estimatedDaysError)}
					type={AlertTypes.Danger}
				/>
			)}

			<div
				className='align-items-center d-flex justify-content-between mb-2'
				style={{height: '34px'}}
			>
				<h3>{Liferay.Language.get('traffic-split')}</h3>

				{trafficSplitTotal !== 100 && (
					<ClayButton
						className='button-root'
						displayType='unstyled'
						onClick={() => {
							setVariants(initialStateVariants(variants));
						}}
						small
					>
						<strong>
							<ClayIcon
								className='icon-root mr-2'
								symbol='undo'
							/>{' '}
							{Liferay.Language.get('reset')}
						</strong>
					</ClayButton>
				)}
			</div>

			{variants &&
				variants.map(({dxpVariantName, trafficSplit}, i) => (
					<div
						className='align-items-center d-flex form-group justify-content-between'
						key={`${dxpVariantName
							.replace(' ', '')
							.toLowerCase()}${i}`}
					>
						<span>{dxpVariantName}</span>
						<div className='align-items-center d-flex'>
							<input
								className='form-control'
								max={MAX_TRAFFIC_SPLIT}
								min={MIN_TRAFFIC_SPLIT}
								onChange={({target: {value}}) => {
									const inputValue = parseInt(value);

									setVariants(
										variants.map((variant, j) => {
											if (i === j) {
												let trafficSplit = MIN_TRAFFIC_SPLIT;

												if (
													inputValue <
														MIN_TRAFFIC_SPLIT ||
													!inputValue
												) {
													trafficSplit = MIN_TRAFFIC_SPLIT;
												} else if (
													inputValue >
													MAX_TRAFFIC_SPLIT
												) {
													trafficSplit = MAX_TRAFFIC_SPLIT;
												} else {
													trafficSplit = inputValue;
												}

												return {
													...variant,
													trafficSplit
												};
											}

											return variant;
										})
									);
								}}
								style={{width: '80px'}}
								type='number'
								value={String(trafficSplit)}
							/>
							<span className='ml-2'>{'%'}</span>
						</div>
					</div>
				))}
			<div
				className={`d-flex justify-content-between ${
					trafficSplitTotal === 100
						? 'text-secondary'
						: 'text-warning'
				}`}
			>
				{trafficSplitTotal === 100 ? (
					<span>{Liferay.Language.get('total-percentage')}</span>
				) : (
					<span>
						{sub(
							Liferay.Language.get(
								'total-percentage-must-equal-x'
							),
							['100%']
						)}
					</span>
				)}
				<span>{`${trafficSplitTotal}%`}</span>
			</div>

			<hr />

			<h3>{Liferay.Language.get('confidence-level')}</h3>

			<div className='align-items-center d-flex justify-content-between'>
				<div>
					<div>
						{Liferay.Language.get(
							'the-required-confidence-level-for-this-test'
						)}
					</div>
					{confidenceLeveInRange ? (
						<div className='font-size-sm text-secondary'>
							{sub(
								Liferay.Language.get(
									'we-suggest-x-but-you-can-enter-a-value-between-x-x'
								),
								['95%', '80%', '99%']
							)}
						</div>
					) : (
						<div className='font-size-sm text-danger'>
							{sub(
								Liferay.Language.get(
									'please-enter-a-value-between-x-x'
								),
								['80%', '99%']
							)}
						</div>
					)}
				</div>
				<div className='align-items-center d-flex'>
					<div
						className={getCN('align-items-center d-flex', {
							'has-error': !confidenceLeveInRange
						})}
					>
						<input
							className='form-control'
							onChange={({target: {value}}) => {
								setConfidenceLevel(value ? parseInt(value) : 0);
							}}
							style={{width: '80px'}}
							type='number'
							value={String(confidenceLevel)}
						/>
						<span className='ml-2'>{'%'}</span>
					</div>
				</div>
			</div>

			<hr />

			<h3>{Liferay.Language.get('estimated-duration')}</h3>

			<div style={{height: '45px', position: 'relative'}}>
				{!estimatedDaysLoading ? (
					<div className='align-items-center d-flex justify-content-between'>
						{estimatedDaysData &&
						estimatedDaysData.experimentEstimatedDaysDuration ? (
							<>
								<div>
									<div>
										{Liferay.Language.get(
											'estimated-time-to-declare-winner'
										)}
									</div>
									<div className='font-size-sm text-secondary'>
										{Liferay.Language.get(
											'estimated-is-based-on-historical-traffic-and-the-traffic-split'
										)}
									</div>
								</div>

								<h2>
									{sub(Liferay.Language.get('x-days'), [
										estimatedDaysData.experimentEstimatedDaysDuration
									])}
								</h2>
							</>
						) : (
							<>
								<div>
									<div>
										{Liferay.Language.get(
											'estimated-time-to-declare-winner-not-available'
										)}
									</div>
									<div className='font-size-sm text-secondary'>
										{Liferay.Language.get(
											'we-dont-have-enough-historical-data-to-make-this-prediction'
										)}
									</div>
								</div>

								<h2>{'-'}</h2>
							</>
						)}
					</div>
				) : (
					<Loading />
				)}
			</div>
		</BaseModal>
	);
};

export default RunExperimentModal;
