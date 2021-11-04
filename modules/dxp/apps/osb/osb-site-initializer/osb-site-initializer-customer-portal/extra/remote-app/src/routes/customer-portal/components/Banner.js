import backgroundImg from "../assets/background.png"


const Banner = () => {

  return (

    <div className="align-items-stretch banner d-flex" style={{ backgroundImage: `url(${backgroundImg})` }} >
      <div className="d-flex flex-column justify-content-between m-3 p-5 text-neutral-10">
        <div className="display-4 font-weight-normal">Welcome, <span className="font-weight-bolder">Sarah!</span></div>

        <div>
          <h2>Projects</h2>

          <div className="font-weight-normal text-paragraph">Select a project to see your subscriptions, activate your products, and view team members.</div>
        </div>
      </div>

    </div>
  );
};

export default Banner;