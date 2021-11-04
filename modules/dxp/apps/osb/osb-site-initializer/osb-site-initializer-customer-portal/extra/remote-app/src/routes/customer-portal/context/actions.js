import { action, actionType } from "~/common/context/actions";

const actionTypes = actionType("ONBOARDING", ["CHANGE_STEP"]);

const changeStep = (payload) => {
    return action(actionTypes.CHANGE_STEP, payload);
};

export { actionTypes, changeStep }