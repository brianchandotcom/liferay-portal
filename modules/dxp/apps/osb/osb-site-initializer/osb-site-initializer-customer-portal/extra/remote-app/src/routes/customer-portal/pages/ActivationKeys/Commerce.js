import {useQuery} from '@apollo/client';
import Table from '../../../../common/components/Table';
import {getKoroneikiAccounts} from '../../../../common/services/liferay/graphql/queries';
import ActivationKeysLayout from '../../components/ActivationKeysLayout';

const Commerce = ({accountKey}) => {
	const {data, loading} = useQuery(getKoroneikiAccounts, {
		variables: {
			filter: `accountKey eq '${accountKey}'`,
		},
	});

	const dxpVersion = data?.c?.koroneikiAccounts?.items[0]?.dxpVersion;

	const instructionsData = [
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
	];

	const columns = [
		{
			accessor: 'version',
			bodyClass: 'border border-0 py-4 pl-4',
			header: {
				name: 'Version',
				styles:
					'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-minw-200 py-3 pl-4',
			},
			headingTitle: true,
		},
		{
			accessor: 'instructions',
			bodyClass: 'border border-0',
			header: {
				name: 'Instructions',
				styles:
					'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
			},
		},
	];

	return (
		<>
			{!loading ? (
				<ActivationKeysLayout>
					{dxpVersion && dxpVersion !== '7.3' ? (
						<ActivationKeysLayout.Inputs
							accountKey={accountKey}
							productKey="commerce"
							productTitle="Commerce"
						/>
					) : (
						<Table
							columns={columns}
							data={instructionsData.map(
								({instructions, version}) => ({
									instructions: Array.isArray(
										instructions
									) ? (
										<div>
											<p className="mb-0 text-neutral-9 text-paragraph">
												{instructions[0]}
											</p>

											<p className="mb-0 text-neutral-7 text-paragraph-sm">
												{instructions[1]}

												<a className="text-neutral-7">
													<u>{instructions[2]} </u>
												</a>
											</p>
										</div>
									) : (
										<p className="mb-0 text-neutral-9 text-paragraph">
											{instructions}
										</p>
									),
									version,
								})
							)}
						/>
					)}
				</ActivationKeysLayout>
			) : (
				<ActivationKeysLayout.Skeleton />
			)}
		</>
	);
};

export default Commerce;
