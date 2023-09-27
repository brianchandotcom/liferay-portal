import Card from 'shared/components/Card';
import ClayNavigationBar from '@clayui/navigation-bar';
import MediansChart from 'experiments/components/variant-card/MediansChart';
import PerDayChart from 'experiments/components/variant-card/PerDayChart';
import React from 'react';
import VariantTableCard from 'experiments/components/variant-card/VariantTableCard';
import {CLASSNAME} from './constants';
import {useStateValue} from 'experiments/state';

const MEDIANS = 'medians';
const PER_DAY = 'per-day';

interface VariantCardIProps {
	label: string;
}

const VariantCard: React.FC<VariantCardIProps> = ({label}) => {
	const [{variantChartTriggered}, dispatch]: any = useStateValue();

	return (
		<Card className={CLASSNAME}>
			<Card.Header>
				<Card.Title>{label}</Card.Title>
			</Card.Header>

			<Card.Body>
				<div className={`${CLASSNAME}-charts`}>
					<ClayNavigationBar
						triggerLabel={
							variantChartTriggered === MEDIANS
								? Liferay.Language.get('medians')
								: Liferay.Language.get('per-day')
						}
					>
						<ClayNavigationBar.Item
							active={variantChartTriggered === MEDIANS}
						>
							<button
								className='btn btn-unstyled btn-block btn-sm nav-link'
								onClick={() =>
									dispatch({
										newAction: MEDIANS,
										type: 'setVariantChartTriggered'
									})
								}
								type='button'
							>
								{Liferay.Language.get('medians')}
							</button>
						</ClayNavigationBar.Item>
						<ClayNavigationBar.Item
							active={variantChartTriggered === PER_DAY}
						>
							<button
								className='btn btn-unstyled btn-block btn-sm nav-link'
								onClick={() =>
									dispatch({
										newAction: PER_DAY,
										type: 'setVariantChartTriggered'
									})
								}
								type='button'
							>
								{Liferay.Language.get('per-day')}
							</button>
						</ClayNavigationBar.Item>
					</ClayNavigationBar>

					{variantChartTriggered === MEDIANS ? (
						<MediansChart />
					) : (
						variantChartTriggered === PER_DAY && <PerDayChart />
					)}

					<VariantTableCard />
				</div>
			</Card.Body>
		</Card>
	);
};

export default VariantCard;
