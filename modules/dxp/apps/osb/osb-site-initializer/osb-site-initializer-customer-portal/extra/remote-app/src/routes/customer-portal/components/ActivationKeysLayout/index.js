import ActivationKeysInputs from './Inputs';
import ActivationKeysSkeleton from './Skeleton';

const ActivationKeysLayout = ({children}) => {
	return (
		<div>
			<h1 className="m-0 py-4">Activation Keys</h1>

			{children}
		</div>
	);
};

ActivationKeysLayout.Inputs = ActivationKeysInputs;
ActivationKeysLayout.Skeleton = ActivationKeysSkeleton;

export default ActivationKeysLayout;
