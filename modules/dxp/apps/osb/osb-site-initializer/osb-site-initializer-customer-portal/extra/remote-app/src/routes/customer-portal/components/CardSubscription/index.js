import {useModal} from '@clayui/modal';
import {useState} from 'react';
import ModalCardSubscription from '../ModalCardSubscription';

const CardSubscription = ({cardSubscriptionData}) => {
	const [visible, setVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const parseAccountSubscriptionTerms = (tagName) => {
		return tagName.toLowerCase().replace(' ', '-');
	};

	const accountSubscriptionERC = `${
		cardSubscriptionData.accountSubscriptionGroupERC
	}_${parseAccountSubscriptionTerms(cardSubscriptionData.name)}`;

	const parseDate = (rawDate) => {
		const date = new Date(rawDate);
		const month = date.toLocaleDateString('default', {month: 'short'});
		const day = date.getDate();
		const year = date.getFullYear();

		return `${month} ${day}, ${year}`;
	};

	return (
		<>
			{visible && (
				<ModalCardSubscription
					accountSubscriptionERC={accountSubscriptionERC}
					observer={observer}
					onClose={onClose}
				/>
			)}
			<div
				className="card-subscription mr-4"
				onClick={() => {
					setVisible(true);
				}}
			>
				<div className="card-body">
					<div
						className="d-flex head-text justify-content-center mb-1 row"
						type="text"
					>
						{cardSubscriptionData?.name || ' - '}
					</div>

					<div
						className="d-flex head-text-2 justify-content-center row"
						type="text"
					>
						{`Instance size: ${
							cardSubscriptionData?.instanceSize || ' - '
						}`}
					</div>

					<div
						className="card-date d-flex justify-content-center mb-4 row"
						type="text"
					>
						{`${parseDate(
							cardSubscriptionData?.startDate
						)} - ${parseDate(cardSubscriptionData?.endDate)}`}
					</div>

					<div className="badge-card-subscription d-flex justify-content-center">
						Active
					</div>
				</div>
			</div>
		</>
	);
};

export default CardSubscription;
