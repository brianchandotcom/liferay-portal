import {useQuery} from '@apollo/client';
import {useMemo} from 'react';
import Table from '../../../../common/components/Table';
import {getKoroneikiAccounts} from '../../../../common/services/liferay/graphql/queries';
import CommerceLicense from './CommerceLicense';
import CommerceSkeleton from './Skeleton';

const Commerce = ({accountKey}) => {
	const {data: dataDXP, loading: loadingDXP} = useQuery(
		getKoroneikiAccounts,
		{
			variables: {
				filter: `accountKey eq '${accountKey}'`,
			},
		}
	);
	const DXP_VERSION = dataDXP
		? dataDXP.c.koroneikiAccounts.items[0]?.dxpVersion
		: '';
	const tableData = useMemo(
		() => [
			{
				instructions: 'All Commerce modules are enabled by default.',
				version: 'DXP 7.4 GA1+',
			},
			{
				instructions: [
					'Commerce is activated using a portal property.',
					'More details: ',
					'Activating Liferay Commerce.',
				],
				version: 'DXP 7.3 FP3/SP2+',
			},
			{
				instructions: [
					'Commerce is activated usinzg a portal property.',
					'To request a new or replacement activation key, please ',
					'open a support ticket.',
				],
				version: 'DXP 7.3 FP3/SP2+',
			},
		],
		[]
	);
	const columns = useMemo(
		() => [
			{
				Header: 'Version',
				accessor: 'version',
				bodyClass: 'border border-0 py-4 pl-4',
				headerClass:
					'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-minw-200 py-3 pl-4',
				headingTitle: true,
			},
			{
				Cell: (props) => {
					return Array.isArray(props.instructions) ? (
						<div>
							<p className="mb-0 text-neutral-9 text-paragraph">
								{props.instructions[0]}
							</p>

							<p className="mb-0 text-neutral-7 text-paragraph-sm">
								{props.instructions[1]}

								<a className="text-neutral-7">
									<u>{props.instructions[2]} </u>
								</a>
							</p>
						</div>
					) : (
						<p className="mb-0 text-neutral-9 text-paragraph">
							{props.instructions}
						</p>
					);
				},
				Header: 'Instructions',
				accessor: 'instructions',
				bodyClass: 'border border-0',
				headerClass:
					'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
			},
		],
		[]
	);

	return (
		<>
			{!loadingDXP ? (
				<div className="commerce-container mb-3">
					<h1 className="h1 mb-5">Activation Keys</h1>

					{DXP_VERSION && Number(DXP_VERSION) < 7.3 ? (
						<CommerceLicense accountKey={accountKey} />
					) : (
						<Table columns={columns} data={tableData} />
					)}
				</div>
			) : (
				<p>Loading...</p>
			)}
		</>
	);
};
Commerce.Skeleton = CommerceSkeleton;
export default Commerce;
