import ClayForm, { ClayInput } from '@clayui/form';
import { useFormikContext } from 'formik';
import { useContext } from "react";
import Input from '~/shared/components/Input';
import Select from '~/shared/components/Select';
import { AppContext } from '../context'
import { getInitialDxpAdmin, getRolesList } from '../utils/constants';
import Layout from './layout';
import BaseButton from "~/shared/components/BaseButton";

const AdminInputs = ({ id }) => {
    return (
        <ClayForm.Group className="mb-0 pb-1">
            <hr className="mb-4 mt-4 mx-3" />
            <Input label="System Admin’s Email Address" name={`admins[${id}].email`} placeholder="username@superbank.com" type="email" groupStyle="pt-1" required />
            <ClayInput.Group className="mb-0">
                <ClayInput.GroupItem className="m-0">
                    <Input label="System Admin’s First Name" name={`admins[${id}].firstName`} type="text" required />
                </ClayInput.GroupItem>
                <ClayInput.GroupItem className="m-0">
                    <Input label="System Admin’s Last Name" name={`admins[${id}].lastName`} type="text" required />
                </ClayInput.GroupItem>
            </ClayInput.Group>
            <Input label="System Admin’s Github Username" name={`admins[${id}].github`} type="text" groupStyle="mb-0" required />
        </ClayForm.Group>
    );
}

const SetupDXP = () => {
    const [state] = useContext(AppContext);
    const { values, setFieldValue, errors } = useFormikContext();

    console.log(errors);

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
                        onClick={() => console.log("Send it!")}
                    >
                        Submit
                    </BaseButton>
                ),
            }}
            headerProps={{
                title: "Set up DXP Cloud",
                helper:
                    "We’ll need a few details to finish building your DXP environment(s).",
            }}
            mainStyles="pl-3 pt-1"
        >
            <div className="d-flex justify-content-between mb-2 pb-1 pl-3">
                <div className="flex-fill">
                    <label>Organization Name</label>
                    <p className="text-neutral-3 text-paragraph-lg">
                        <strong>{state.dxp.organization}</strong>
                    </p>
                </div>
                <div className="flex-fill">
                    <label>Liferay DXP Version</label>
                    <p className="text-neutral-3 text-paragraph-lg">
                        <strong>{state.dxp.version}</strong>
                    </p>
                </div>
            </div>
            <ClayForm.Group className="mb-0">
                <ClayForm.Group className="mb-0 pb-1">
                    <Input label="Project ID" helper="Lowercase letters and numbers only. Project IDs cannot be change." name="dxp.projectId" placeholder="superbank1" type="text" groupStyle="pb-1" required />
                    <Select
                        name="dxp.dataCenterRegion"
                        label="Primary Data Center Region"
                        options={getRolesList().map((option) => (
                            {
                                label: option.name,
                                value: option.id
                            }
                        ))}
                        required
                        groupStyle="mb-0"
                    />
                </ClayForm.Group>

                {values.dxp.admins.map((admin, index) => (
                    <AdminInputs id={index} key={index} />
                ))}
            </ClayForm.Group>
            <BaseButton
                onClick={() => setFieldValue("dxp.admins", [...values.dxp.admins, getInitialDxpAdmin()])}
                prependIcon="plus"
                className="ml-3 my-2 text-brand-primary"
                small
                borderless
            >
                Add Another Admin
            </BaseButton>
        </Layout>
    );
};

export default SetupDXP;
