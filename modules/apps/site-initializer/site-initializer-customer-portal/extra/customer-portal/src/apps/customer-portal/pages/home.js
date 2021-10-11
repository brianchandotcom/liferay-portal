import ProjectCard from "../components/ProjectCard";
import SearchProject from "../components/SearchProject";
import { status } from '../utils/constants';

const projects = [
  {
    title: "Super1",
    status: status.active,
    endDate: "Jul 19, 2050",
    state: "United States",
    subtitle: "Digitals"
  },
  {
    title: "Super1",
    status: status.active,
    endDate: "Jul 19, 2050",
    state: "United States",
    subtitle: "Digitals"
  },
  {
    title: "Super1",
    status: status.active,
    endDate: "Jul 19, 2050",
    state: "United States",
    subtitle: "Digitals"
  },
  {
    title: "Super1",
    status: status.active,
    endDate: "Jul 19, 2050",
    state: "United States",
    subtitle: "Digitals"
  },
  {
    title: "Super1",
    status: status.active,
    endDate: "Jul 19, 2050",
    state: "United States",
    subtitle: "Digitals"
  }
];

const Home = () => {
  const handleChange = (value) => {
    console.log("handle!");
  };

  return (
    <div className="mt-5 ml-8 pt-2">
      <div className={`display-4 font-weight-bold mb-5${projects.length > 4 ? " pb-2" : ""} text-neutral-0`}>Projects</div>
      {projects.length > 4 && <div className="d-flex justify-content-between align-items-center mb-4">
        <SearchProject onChange={(event) => handleChange(event.target.value)} placeholder="Find a project" />
        <h5 className="text-neutral-3 m-0">{projects.length} projects</h5>
      </div>}
      <div className={`d-flex flex-wrap home-projects${projects.length > 4 ? "-sm pt-2" : ""}`}>
        {projects.map((project, index) => <ProjectCard key={index} {...project} small={projects.length > 4} />)}
      </div>
    </div>
  );
};

export default Home;
