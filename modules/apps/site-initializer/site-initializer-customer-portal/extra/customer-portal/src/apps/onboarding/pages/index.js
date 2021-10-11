import { useContext } from "react";
import { AppContext } from "../context";
import { steps } from "../utils/constants";
import Invites from "./invites";
import SetupDXP from "./setup.dxp";
import Welcome from "./welcome";

const Pages = () => {
    const [{ step }] = useContext(AppContext);

    switch (step) {
        case steps.invites: {
            return <Invites />;
        }

        case steps.dxp: {
            return <SetupDXP />
        }
        default: {
            return <Welcome />;
        }
    }
};

export default Pages;