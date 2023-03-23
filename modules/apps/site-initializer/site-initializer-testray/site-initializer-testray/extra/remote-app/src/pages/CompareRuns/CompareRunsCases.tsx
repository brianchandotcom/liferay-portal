import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import i18n from '~/i18n';
import {TestrayCase, TestrayCaseResult, TestrayRun} from '~/services/rest';
import {testrayCaseRest} from '~/services/rest/TestrayCase';
const nestedFieldsAndFilds =
	'?nestedFields=componentToCases,caseToCaseResult,runToCaseResult&nestedFieldsDepth=2&fields=priority,caseToCaseResult.r_runToCaseResult_c_runId,name,r_componentToCases_c_component.name,caseToCaseResult.dueStatus,caseToCaseResult.id';
const CompareRunsCases = () => {
	const {runA, runB} = useParams();

	const caseResultFilter = useSearchBuilder({useURIEncode: false});

	const filter = caseResultFilter
		.in('caseToCaseResult/r_runToCaseResult_c_runId', [
			String(runA),
			String(runB),
		])
		.build();

	return (
		<Container>
			<ListView
				initialContext={{pageSize: 100}}
				managementToolbarProps={{
					display: {columns: false},
					filterSchema: 'compareRunsCases',
					title: i18n.translate('cases'),
				}}
				resource={`/${testrayCaseRest.uri}${nestedFieldsAndFilds}`}
				tableProps={{
					columns: [
						{
							key: 'priority',
							sorteable: true,
							value: i18n.translate('priority'),
							width: '100',
						},
						{
							key: 'component',
							render: (component) => component?.name,
							value: i18n.translate('component'),
						},
						{
							key: 'name',
							size: 'xl',
							sorteable: true,
							value: i18n.translate('case'),
						},
						{
							key: 'dueStatus',
							size: 'md',
							sorteable: true,
							value: i18n.sub('status-in-x', 'run-a'),
						{
							key: 'dueStatus',
							size: 'md',
							sorteable: true,
							value: i18n.sub('status-in-x', 'run-b'),
						},
					],
					rowWrap: true,
				}}
				transformData={(response) =>
					testrayCaseRest.transformDataFromList(response)
				}
				variables={{
					filter,
				}}
			/>
		</Container>
	);
};
export default CompareRunsCases;
