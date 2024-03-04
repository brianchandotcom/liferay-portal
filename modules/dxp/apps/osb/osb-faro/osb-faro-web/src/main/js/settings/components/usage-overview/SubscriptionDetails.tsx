import Card from 'shared/components/Card';
import getCN from 'classnames';
import React from 'react';
import {
	getPlanAddOns,
	getPropLabel,
	INDIVIDUALS,
	PAGEVIEWS,
	PLANS
} from 'shared/util/subscriptions';
import {Text} from '@clayui/core';

const ItemRenderer = ({name, value}) => (
	<div className='d-flex justify-content-between mb-1'>
		<Text color='secondary' size={3} weight='semi-bold'>
			{name}
		</Text>

		<Text color='secondary' size={3}>
			{value}
		</Text>
	</div>
);

export const SubscriptionDetails = ({currentPlan, planType}) => {
	const addOns = getPlanAddOns(currentPlan);

	const showAddOns = !!Object.keys(addOns).length;

	return (
		<Card testId='subscription-details'>
			<Card.Header className='py-2'>
				<Card.Title>
					<Text color='secondary' size={3}>
						{Liferay.Language.get(
							'subscription-details'
						).toUpperCase()}
					</Text>
				</Card.Title>
			</Card.Header>

			<hr className='m-0' />

			<Card.Body className={getCN({'pb-0': showAddOns})}>
				<div className='mb-2'>
					<Text size={3} weight='semi-bold'>
						{getPropLabel(currentPlan.name)}
					</Text>
				</div>

				<ItemRenderer
					name={Liferay.Language.get('individuals')}
					value={PLANS[planType].limits[INDIVIDUALS].toLocaleString()}
				/>

				<ItemRenderer
					name={Liferay.Language.get('page-views')}
					value={PLANS[planType].limits[PAGEVIEWS].toLocaleString()}
				/>

				<ItemRenderer
					name={Liferay.Language.get('workspace-users')}
					value={Liferay.Language.get('unlimited')}
				/>
			</Card.Body>

			{!!Object.keys(addOns).length && (
				<>
					<Card.Header className='pb-2'>
						<Card.Title>
							<Text color='secondary' size={3}>
								{Liferay.Language.get(
									'purchased-add-ons'
								).toUpperCase()}
							</Text>
						</Card.Title>
					</Card.Header>

					<Card.Body>
						<hr className='mt-0' />

						<div className='mb-2'>
							<Text color='secondary' size={3}>
								{Liferay.Language.get(
									'tailor-limits-to-business-needs.-incrementally-increase-individual-or-page-view-limits-as-needed-without-committing-to-a-new-plan'
								)}
							</Text>
						</div>

						<ItemRenderer
							name={Liferay.Language.get('individuals')}
							value={addOns[INDIVIDUALS]}
						/>

						<ItemRenderer
							name={Liferay.Language.get('page-views')}
							value={addOns[PAGEVIEWS]}
						/>
					</Card.Body>
				</>
			)}
		</Card>
	);
};
