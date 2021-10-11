import { useContext } from "react";
import ClayForm, { ClayInput } from '@clayui/form';
import { getInitialInvite, getRolesList, steps } from "../utils/constants";
import { AppContext } from "../context";
import { changeStep } from "../context/actions";
import Layout from "./layout";
import { useFormikContext } from "formik";
import Input from "~/shared/components/Input";
import Select from "~/shared/components/Select";
import BaseButton from "~/shared/components/BaseButton";

const HorizontalInputs = ({ id }) => {
    return (
        <ClayInput.Group>
            <ClayInput.GroupItem className="m-0">
                <Input
                    name={`invites[${id}].email`}
                    placeholder="username@superbank.com"
                    type="email"
                    label="Email"
                    groupStyle="m-0"
                />
            </ClayInput.GroupItem>
            <ClayInput.GroupItem className="m-0">
                <Select
                    name={`invites[${id}].roleId`}
                    label="Role"
                    options={getRolesList().map((option) => (
                        {
                            label: option.name,
                            value: option.id
                        }
                    ))}
                    groupStyle="m-0"
                />
            </ClayInput.GroupItem>
        </ClayInput.Group>
    );
};

const Invites = () => {
    const [, dispatch] = useContext(AppContext);
    const { values, setFieldValue } = useFormikContext();

    return (
        <Layout
            footerProps={{
                leftButton: (
                    <BaseButton
                        borderless
                        onClick={() => console.log("Skipped")}
                    >
                        Skip for now
                    </BaseButton>
                ),
                middleButton: (
                    <BaseButton
                        displayType="primary"
                        onClick={() => dispatch(changeStep(steps.dxp))}
                    >
                        Send Invitations
                    </BaseButton>
                ),
            }}
            headerProps={{
                title: "Invite Your Team Members",
                helper:
                    "Team members will receive an email invitation to access this project on Customer Portal.",
            }}
        >
            <div className="invites-form px-3 overflow-auto">
                <ClayForm.Group className="m-0">
                    {values.invites.map((invite, index) => (
                        <HorizontalInputs id={index} key={index} />
                    ))}
                </ClayForm.Group>
                <BaseButton
                    onClick={() => setFieldValue("invites", [...values.invites, getInitialInvite()])}
                    prependIcon="plus"
                    className="mb-3 ml-3 mt-2 text-brand-primary"
                    small
                    borderless
                >
                    Add More Members
                </BaseButton>
            </div>
            <div className="invites-helper px-3">
                <hr className="mt-0 mx-3" />
                <div className="mx-3">
                    <a className="btn font-weight-bold p-0 text-link-sm" href="https://liferay.com/pt" target="_blank" rel="noreferrer">Learn more about Customer Portal roles</a>
                </div>
            </div>
        </Layout>
    );
};

export default Invites;
