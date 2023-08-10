import Card from 'shared/components/Card';
import React, {useState} from 'react';
import {CLASSNAME} from '../summary-base-card/constants';
import {Step} from '../summary-base-card/types';
import {useModal} from '@clayui/modal';

interface ISummaryCardDraftStepBodyProps
	extends React.HTMLAttributes<HTMLElement> {
	status: string;
	step: Step;
}

const SummaryCardDraftStepBody: React.FC<ISummaryCardDraftStepBodyProps> = ({
	status,
	step: {Description, modal, title}
}) => {
	const [visibleModal, setVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false)
	});

	const renderModal = () => {
		const {Component, props} = modal;

		if (!visibleModal) return null;

		return <Component {...props} observer={observer} onClose={onClose} />;
	};

	return (
		<Card
			className={`${CLASSNAME}-step-content ${CLASSNAME}-step-content-${status}`}
		>
			<Card.Body>
				<h4>{title}</h4>

				<Description
					className={`${CLASSNAME}-step-content-description`}
				/>

				{modal && renderModal()}
			</Card.Body>
		</Card>
	);
};

export default SummaryCardDraftStepBody;
