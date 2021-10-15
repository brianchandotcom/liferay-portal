import ProjectCard from '../components/ProjectCard';
import SearchProject from '../components/SearchProject';
import {status} from '../utils/constants';

const projects = [
	{
		endDate: 'Jul 19, 2050',
		state: 'United States',
		status: status.active,
		subtitle: 'Digitals',
		title: 'Super1',
	},
	{
		endDate: 'Jul 19, 2050',
		state: 'United States',
		status: status.active,
		subtitle: 'Digitals',
		title: 'Super1',
	},
	{
		endDate: 'Jul 19, 2050',
		state: 'United States',
		status: status.active,
		subtitle: 'Digitals',
		title: 'Super1',
	},
	{
		endDate: 'Jul 19, 2050',
		state: 'United States',
		status: status.active,
		subtitle: 'Digitals',
		title: 'Super1',
	},
	{
		endDate: 'Jul 19, 2050',
		state: 'United States',
		status: status.active,
		subtitle: 'Digitals',
		title: 'Super1',
	},
];

const Home = () => {
	return (
		<div className="ml-8 mt-5 pt-2">
			<div
				className={`display-4 font-weight-bold mb-5${
					projects.length > 4 ? ' pb-2' : ''
				} text-neutral-0`}
			>
				Projects
			</div>
			{projects.length > 4 && (
				<div className="align-items-center d-flex justify-content-between mb-4">
					<SearchProject placeholder="Find a project" />

					<h5 className="m-0 text-neutral-3">
						{projects.length} projects
					</h5>
				</div>
			)}
			<div
				className={`d-flex flex-wrap home-projects${
					projects.length > 4 ? '-sm pt-2' : ''
				}`}
			>
				{projects.map((project, index) => (
					<ProjectCard
						key={index}
						{...project}
						small={projects.length > 4}
					/>
				))}
			</div>
		</div>
	);
};

export default Home;
