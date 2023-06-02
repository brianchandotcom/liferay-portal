import * as API from 'shared/api';
import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import Form, {validateRequired} from 'shared/components/form';
import NavigationWarning from 'shared/components/NavigationWarning';
import Promise from 'metal-promise';
import React from 'react';
import SegmentEditStatic from 'segment/segment-editor/static/SegmentEditStatic';
import Sheet from 'shared/components/Sheet';
import withBaseEdit from 'contacts/hoc/segment/WithBaseEdit';
import {Changeset} from 'shared/util/records';
import {get} from 'lodash';
import {PropTypes} from 'prop-types';
import {Routes, SEGMENTS, toRoute} from 'shared/util/router';
import {Segment} from 'shared/util/records';
import {SegmentTypes} from 'shared/util/constants';
import {sub} from 'shared/util/lang';

export class StaticSegmentEdit extends React.Component {
	static propTypes = {
		channelId: PropTypes.string,
		editing: PropTypes.bool.isRequired,
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string,
		onDelete: PropTypes.bool,
		onSubmit: PropTypes.func,
		segment: PropTypes.instanceOf(Segment)
	};

	state = {
		changeset: new Changeset()
	};

	componentDidMount() {
		this._formRef = React.createRef();
	}

	@autobind
	createStaticSegment(form) {
		return this.updateSegment({
			changeset: this.state.changeset,
			name: form.name.trim(),
			segmentType: SegmentTypes.Static
		});
	}

	isValidStaticSegment() {
		const {
			props: {segment},
			state: {changeset}
		} = this;

		const {errors = {}} = this.getFormikBag() || {};

		const validName = !errors.name;

		const removedEntireMembership =
			segment &&
			changeset.removed.size === segment.individualCount &&
			changeset.added.isEmpty();

		const validExistingSegment =
			segment &&
			segment.individualCount &&
			validName &&
			!removedEntireMembership;

		const validNewSegment =
			!segment && !changeset.added.isEmpty() && validName;

		return validNewSegment || validExistingSegment;
	}

	getFormActions() {
		const {channelId, editing, groupId, id} = this.props;

		return (
			<div className='form-actions'>
				<ClayLink
					button
					className='button-root cancel'
					displayType='secondary'
					href={
						editing
							? toRoute(Routes.CONTACTS_ENTITY, {
									channelId,
									groupId,
									id,
									type: SEGMENTS
							  })
							: toRoute(Routes.CONTACTS_LIST_ENTITY, {
									channelId,
									groupId,
									type: SEGMENTS
							  })
					}
				>
					{Liferay.Language.get('cancel')}
				</ClayLink>

				<ClayButton
					className='button-root submit'
					disabled={!this.isValidStaticSegment()}
					displayType='primary'
					type='submit'
				>
					{editing
						? Liferay.Language.get('save')
						: Liferay.Language.get('create')}
				</ClayButton>
			</div>
		);
	}

	getFormikBag() {
		const form = get(this, ['_formRef', 'current']);

		return form ? form.getFormikBag() : {};
	}

	@autobind
	handleStaticSegmentUpdate(newVal) {
		this.setState({changeset: newVal});
	}

	@autobind
	handleSubmit(form) {
		this.props.onSubmit(form, this._formRef, this.createStaticSegment);
	}

	hasNavigationWarning(isSubmitting, onDelete) {
		const {changeset} = this.state;

		return (
			!isSubmitting &&
			!onDelete &&
			(!changeset.get('added').isEmpty() ||
				!changeset.get('removed').isEmpty())
		);
	}

	updateSegment(data) {
		const {channelId, groupId, id} = this.props;

		const request = id
			? API.individualSegment.update
			: API.individualSegment.create;

		const {changeset, ...requestData} = data;

		const addedIds = changeset.added.keySeq().toArray();

		const removedIds = changeset.removed.keySeq().toArray();

		const requests = [
			request(
				id
					? {channelId, groupId, id, name: requestData.name}
					: {
							channelId,
							groupId,
							individualIds: addedIds,
							...requestData
					  }
			)
		];

		if (id && addedIds.length) {
			requests.push(
				API.individualSegment.addMemberships({
					groupId,
					id,
					individualIds: addedIds
				})
			);
		}
		if (id && removedIds.length) {
			requests.push(
				API.individualSegment.removeMemberships({
					groupId,
					id,
					individualIds: removedIds
				})
			);
		}

		return Promise.all(requests);
	}

	render() {
		const {
			props: {channelId, editing, groupId, id, onDelete, segment},
			state: {changeset}
		} = this;

		return (
			<Form
				initialValues={{
					name: segment ? segment.name : '',
					segmentType: SegmentTypes.Static
				}}
				onSubmit={this.handleSubmit}
				ref={this._formRef}
			>
				{({handleSubmit, isSubmitting, isValid}) => (
					<Form.Form onSubmit={handleSubmit}>
						<Sheet className='d-flex flex-column' pageDisplay>
							<NavigationWarning
								when={this.hasNavigationWarning(
									isSubmitting,
									onDelete
								)}
							/>
							<Sheet.Header divider>
								<h2 className='segment-form-title'>
									{editing
										? sub(
												Liferay.Language.get(
													'edit-x-segment'
												),
												[
													SegmentTypes.Static.toLowerCase()
												]
										  )
										: Liferay.Language.get(
												'define-segment'
										  )}
								</h2>
							</Sheet.Header>

							<Sheet.Body className='form-content form-content-static d-flex flex-column flex-grow-1'>
								<div className='row'>
									<div className='col-lg-4'>
										<Form.Group
											autoFit
											className='name-container'
										>
											<Form.GroupItem>
												<Form.Input
													inline
													label={Liferay.Language.get(
														'name'
													)}
													name='name'
													placeholder={Liferay.Language.get(
														'segment-name'
													)}
													required
													validate={validateRequired}
												/>
											</Form.GroupItem>
										</Form.Group>
									</div>
								</div>

								<div className='row flex-grow-1'>
									<div className='form-inputs-container col-lg-12'>
										{/* eslint-disable react/jsx-handler-names */}
										<SegmentEditStatic
											changeset={changeset}
											channelId={channelId}
											entityLabel={Liferay.Language.get(
												'individuals'
											)}
											groupId={groupId}
											id={id}
											membershipCount={
												segment
													? segment.individualCount
													: undefined
											}
											onChange={
												this.handleStaticSegmentUpdate
											}
										/>
										{/* eslint-enable react/jsx-handler-names */}
									</div>
								</div>

								<div>{this.getFormActions(isValid)}</div>
							</Sheet.Body>
						</Sheet>
					</Form.Form>
				)}
			</Form>
		);
	}
}

export default withBaseEdit(StaticSegmentEdit);
