import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React, {useRef, useState} from 'react';
import {CLASSNAME} from './constants';
import {Modal, Status} from './types';
import {useModal} from '@clayui/modal';

interface SummaryBaseCardIProps extends React.HTMLAttributes<HTMLElement> {
	status?: Status;
}

interface SummaryBaseCardHeaderIProps
	extends React.HTMLAttributes<HTMLDivElement> {
	modals?: Array<Modal>;
	cardModals?: Array<Modal>;
	close?: Function;
	open?: Function;
}

const Body: React.FC<React.HTMLAttributes<HTMLElement>> = ({children}) => (
	<Card.Body>{children}</Card.Body>
);

const Footer: React.FC<React.HTMLAttributes<HTMLElement>> = ({children}) => (
	<Card.Footer>{children}</Card.Footer>
);

const Header: React.FC<SummaryBaseCardHeaderIProps> = ({
	cardModals,
	children,
	modals
}) => {
	const [actionActive, setActionActive] = useState(false);
	const [cardActionActive, setCardActionActive] = useState(false);
	const triggerElementRef = useRef(null);
	const menuElementRef = useRef(null);

	const [visibleModal, setVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false)
	});

	const [modal, setModal] = useState({Component: null, props: null});

	return (
		<>
			<Card.Header className='d-flex justify-content-between'>
				{children}

				<div className='d-flex align-items-center'>
					{modals && !!modals.length && (
						<ClayButton.Group>
							<ClayButton
								className='button-root'
								displayType='secondary'
								onClick={
									modals[0].Component &&
									(() => {
										setModal({
											Component: modals[0].Component,
											props: modals[0].props
										});
										setVisibleModal(true);
									})
								}
								small
							>
								{modals[0].title}
							</ClayButton>

							{modals.length > 1 && (
								<>
									<ClayButton
										aria-label={Liferay.Language.get(
											'down'
										)}
										className='button-root'
										displayType='secondary'
										onClick={() =>
											setActionActive(!actionActive)
										}
										ref={triggerElementRef}
										small
									>
										<ClayIcon
											className='icon-root'
											symbol='caret-bottom'
										/>
									</ClayButton>
									<ClayDropDown.Menu
										active={actionActive}
										alignElementRef={triggerElementRef}
										onSetActive={setActionActive}
										ref={menuElementRef}
									>
										<ClayDropDown.ItemList>
											{modals
												.slice(1)
												.map(
													(
														{
															Component,
															props,
															title
														},
														i
													) => (
														<ClayDropDown.Item
															className='c-pointer'
															key={i}
															onClick={
																Component &&
																(() => {
																	setModal({
																		Component,
																		props
																	});
																	setVisibleModal(
																		true
																	);
																})
															}
														>
															{title}
														</ClayDropDown.Item>
													)
												)}
										</ClayDropDown.ItemList>
									</ClayDropDown.Menu>
								</>
							)}
						</ClayButton.Group>
					)}

					{/* TODO (LRAC-14220): check and evalute if this button will redirect to DXP
					So the user can do an action there: terminate, delete, etc */}

					{cardModals &&
						!!cardModals.length &&
						(cardModals.length > 1 ? (
							<ClayDropDown
								active={cardActionActive}
								className='ml-4'
								onActiveChange={setCardActionActive}
								trigger={
									<ClayButton
										aria-label={Liferay.Language.get(
											'menu'
										)}
										className='button-root text-white'
										displayType='unstyled'
									>
										<ClayIcon
											className='icon-root'
											symbol='ellipsis-v'
										/>
									</ClayButton>
								}
							>
								<ClayDropDown.ItemList>
									{cardModals.map(
										({Component, props, title}, i) => (
											<ClayDropDown.Item
												className='c-pointer'
												key={i}
												onClick={
													Component &&
													(() => {
														setModal({
															Component,
															props
														});
														setVisibleModal(true);
													})
												}
											>
												{title}
											</ClayDropDown.Item>
										)
									)}
								</ClayDropDown.ItemList>
							</ClayDropDown>
						) : (
							<ClayButton
								displayType='secondary'
								onClick={
									cardModals[0].Component &&
									(() => {
										setModal({
											Component: cardModals[0].Component,
											props: cardModals[0].props
										});
										setVisibleModal(true);
									})
								}
								size='sm'
							>
								{cardModals[0].title}
							</ClayButton>
						))}
				</div>
			</Card.Header>

			{visibleModal && (
				<modal.Component
					{...modal.props}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
};

const SummaryBaseCard: React.FC<SummaryBaseCardIProps> & {
	Body: typeof Body;
	Footer: typeof Footer;
	Header: typeof Header;
} = ({children, status}) => (
	<Card
		className={getCN(CLASSNAME, {
			[`${CLASSNAME}-status ${CLASSNAME}-status-${status}`]: status
		})}
	>
		{children}
	</Card>
);

SummaryBaseCard.Body = Body;
SummaryBaseCard.Footer = Footer;
SummaryBaseCard.Header = Header;

export default SummaryBaseCard;
