import ProjectCard from '../../components/ProjectCard';

const HomeSkeleton = () => {
	return (
		<div className="col-8 mx-auto pl-6">
			<div className="d-flex flex-column">
				<div className="d-flex flex-wrap home-projects">
					<ProjectCard.Skeleton />

					<ProjectCard.Skeleton />
				</div>
			</div>
		</div>
	);
};

export default HomeSkeleton;
