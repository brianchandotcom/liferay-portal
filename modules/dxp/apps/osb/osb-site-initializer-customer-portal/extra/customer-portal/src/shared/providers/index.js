import ClayProvider from "./ClayProvider";

const Providers = ({ children }) => {
    return (
        <ClayProvider>
            {children}
        </ClayProvider>
    );
}

export default Providers;