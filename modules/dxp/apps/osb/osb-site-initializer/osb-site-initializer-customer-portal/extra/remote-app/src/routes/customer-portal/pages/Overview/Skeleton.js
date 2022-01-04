import ProjectContacts from '../../components/ProjectContacts';
import QuickLinksPanel from '../../components/QuickLinksPanel';

const OverviewSkeleton = () => {
	return (
		<div className="d-flex position-relative w-100">
			<div className="w-100">
				<ProjectContacts.Skeleton />
			</div>

			<QuickLinksPanel.Skeleton />
		</div>
	);
};

export default OverviewSkeleton;
