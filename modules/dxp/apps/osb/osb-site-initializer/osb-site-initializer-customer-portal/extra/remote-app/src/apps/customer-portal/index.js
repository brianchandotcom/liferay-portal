import CustomerPortalStyles from "~/apps/customer-portal/styles/app.scss";
import Pages from "./pages";

const CustomerPortal = () => {
  return (
    <>
      <style>{CustomerPortalStyles}</style>
      <Pages />
    </>
  );
};

export default CustomerPortal;
