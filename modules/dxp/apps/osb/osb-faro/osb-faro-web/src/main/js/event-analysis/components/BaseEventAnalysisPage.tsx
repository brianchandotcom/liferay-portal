import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import EventAnalysisEditor from '../components/event-analysis-editor';
import EventAnalysisToolbar from '../components/EventAnalysisToolbar';
import Form from 'shared/components/form';
import NavigationWarning from 'shared/components/NavigationWarning';
import React, {useContext, useMemo, useState} from 'react';
import withCurrentUser from 'shared/hoc/WithCurrentUser';
import {addAlert} from 'shared/actions/alerts';
import {Alert, RangeSelectors} from 'shared/types';
import {ApolloError} from 'apollo-client';
import {AttributesContext} from '../components/event-analysis-editor/context/attributes';
import {
	Breakdowns,
	CalculationTypes,
	Event,
	Filters
} from 'event-analysis/utils/types';
import {close, modalTypes, open} from 'shared/actions/modals';
import {compose, withRangeKey} from 'shared/hoc';
import {connect, ConnectedProps} from 'react-redux';
import {
	CreateEventAnalysisMutation,
	EventAnalysisMutationData,
	EventAnalysisMutationVariables,
	UpdateEventAnalysisMutation
} from 'event-analysis/queries/EventAnalysisQuery';
import {getSafeRangeSelectors} from 'shared/util/util';
import {GraphQLError} from 'graphql';
import {hasChanges} from 'shared/util/react';
import {omit} from 'lodash';
import {Routes, toRoute} from 'shared/util/router';
import {useHistory, useParams} from 'react-router-dom';
import {useMutation} from '@apollo/react-hooks';
import {User} from 'shared/util/records';
import {WithRangeKeyProps} from 'shared/hoc/WithRangeKey';

enum MessageKeys {
	NameCannotBeBlank = 'name-cannot-be-blank',
	NameIsAlreadyUsed = 'name-is-already-used'
}

interface Error extends ApolloError {
	graphQLErrors: ReadonlyArray<
		GraphQLError & {
			messageKey: keyof MessageKeys;
		}
	>;
}

const ERRORS = {
	[MessageKeys.NameCannotBeBlank]: {
		alertType: Alert.Types.Error,
		message: Liferay.Language.get('name-cannot-be-blank')
	},
	[MessageKeys.NameIsAlreadyUsed]: {
		alertType: Alert.Types.Warning,
		message: Liferay.Language.get(
			'this-analysis-name-is-currently-in-use.-please-try-a-different-one'
		)
	}
};

const PAGE_NAME = 'Event Analysis Editor';

const connector = connect(null, {
	addAlert,
	close,
	open
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IBaseEventAnalysisPageProps
	extends WithRangeKeyProps,
		PropsFromRedux,
		React.HTMLAttributes<HTMLElement> {
	breakdowns?: Breakdowns;
	compareToPrevious?: boolean;
	currentUser: User;
	event?: Event;
	filters?: Filters;
	name?: string;
}

const BaseEventAnalysisPage: React.FC<IBaseEventAnalysisPageProps> = ({
	addAlert,
	close,
	compareToPrevious: initialCompareToPrevious = false,
	currentUser,
	event: initialEvent = null,
	name: initialName = '',
	open,
	rangeSelectors: initialRangeSelectors
}) => {
	const history = useHistory();
	const {channelId, groupId, id: eventAnalysisId = null} = useParams();

	const [compareToPrevious, setCompareToPrevious] = useState<boolean>(
		initialCompareToPrevious
	);
	const [event, setEvent] = useState<Event>(initialEvent);
	const [rangeSelectors, setRangeSelectors] = useState<RangeSelectors>(
		initialRangeSelectors
	);
	const [submitted, setSubmitted] = useState<boolean>(false);
	const [type, setType] = useState<CalculationTypes>(CalculationTypes.Total);

	const {
		breakdownOrder,
		breakdowns,
		changed: attributesContextChanged,
		filterOrder,
		filters
	} = useContext(AttributesContext);

	const Mutation = eventAnalysisId
		? UpdateEventAnalysisMutation
		: CreateEventAnalysisMutation;

	const [saveEventAnalysis] = useMutation<
		EventAnalysisMutationData,
		EventAnalysisMutationVariables
	>(Mutation);

	const handleSubmit = ({name}, {setSubmitting}) => {
		open(
			modalTypes.LOADING_MODAL,
			{
				message: Liferay.Language.get('this-will-only-take-a-moment'),
				title: eventAnalysisId
					? Liferay.Language.get('creating')
					: Liferay.Language.get('updating')
			},
			{closeOnBlur: false}
		);

		saveEventAnalysis({
			variables: {
				analysisType: type,
				channelId,
				compareToPrevious,
				eventAnalysisBreakdowns: breakdownOrder.map(breakdownId =>
					omit(breakdowns[breakdownId], 'id')
				),
				eventAnalysisFilters: filterOrder.map(filterId =>
					omit(filters[filterId], 'id')
				),
				eventAnalysisId,
				eventDefinitionId: event.id,
				name,
				userId: currentUser.userId,
				userName: currentUser.name,
				...getSafeRangeSelectors(rangeSelectors)
			}
		})
			.then(() => {
				setSubmitting(false);
				setSubmitted(true);

				close();

				history.push(
					toRoute(Routes.EVENT_ANALYSIS, {
						channelId,
						groupId
					})
				);

				addAlert({
					alertType: Alert.Types.Success,
					message: Liferay.Language.get(
						'the-analysis-was-saved-successfully'
					)
				});
			})
			.catch(({graphQLErrors}: Error) => {
				setSubmitting(false);
				setSubmitted(false);

				close();

				const {alertType, message} = ERRORS[
					graphQLErrors[0].messageKey
				];

				addAlert({
					alertType,
					message,
					timeout: false
				});
			});
	};

	const compareToPreviousChanged: boolean =
		initialCompareToPrevious !== compareToPrevious;

	const eventChanged: boolean = useMemo(
		() => hasChanges<Event>(initialEvent || {}, event || {}, 'id'),
		[initialEvent, event]
	);

	const rangeSelectorsChanged: boolean = useMemo(
		() =>
			hasChanges<RangeSelectors>(
				initialRangeSelectors,
				rangeSelectors,
				'rangeStart',
				'rangeKey',
				'rangeEnd'
			),
		[initialRangeSelectors, rangeSelectors]
	);

	const onCompareToPreviousChange = (compareToPrevious: boolean) => {
		setCompareToPrevious(compareToPrevious);

		analytics.track(`${PAGE_NAME} - Compared to Previous`);
	};

	const onEventChange = (event: Event) => {
		setEvent(event);

		if (event) {
			const {displayName, name, type} = event;

			analytics.track(`${PAGE_NAME} - Selected an Event`, {
				name: displayName || name,
				type
			});
		}
	};

	const onRangeSelectorsChange = (rangeSelectors: RangeSelectors) => {
		setRangeSelectors(rangeSelectors);

		const {rangeEnd, rangeKey, rangeStart} = rangeSelectors;
		analytics.track(`${PAGE_NAME} - Changed Event Time Period`, {
			dateEnd: rangeEnd,
			dateStart: rangeStart,
			rangeKey
		});
	};

	const onTypeChange = (type: CalculationTypes) => {
		setType(type);

		analytics.track(`${PAGE_NAME} - Changed Calculation Type`, {
			type
		});
	};

	return (
		<BasePage
			className='create-event-analysis-root'
			documentTitle={Liferay.Language.get('event-analysis')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: Liferay.Language.get('home')
					})
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					title={Liferay.Language.get('event-analysis')}
				/>
			</BasePage.Header>

			<Form
				initialValues={{
					name: initialName
				}}
				onSubmit={handleSubmit}
			>
				{({dirty, handleSubmit, isSubmitting, values: {name}}) => {
					const hasChanges =
						attributesContextChanged ||
						dirty ||
						compareToPreviousChanged ||
						eventChanged ||
						rangeSelectorsChanged;

					return (
						<Form.Form onSubmit={handleSubmit}>
							<NavigationWarning
								when={!submitted && hasChanges && !isSubmitting}
							/>

							<BasePage.SubHeader>
								<EventAnalysisToolbar
									isValid={
										!!name &&
										!!event?.id &&
										hasChanges &&
										!isSubmitting
									}
								/>
							</BasePage.SubHeader>
						</Form.Form>
					);
				}}
			</Form>

			<BasePage.Body>
				<EventAnalysisEditor
					channelId={channelId}
					compareToPrevious={compareToPrevious}
					event={event}
					onCompareToPreviousChange={onCompareToPreviousChange}
					onEventChange={onEventChange}
					onRangeSelectorsChange={onRangeSelectorsChange}
					onTypeChange={onTypeChange}
					rangeSelectors={rangeSelectors}
					type={type}
				/>
			</BasePage.Body>
		</BasePage>
	);
};

export default compose<any>(
	connector,
	withCurrentUser,
	withRangeKey
)(BaseEventAnalysisPage);
