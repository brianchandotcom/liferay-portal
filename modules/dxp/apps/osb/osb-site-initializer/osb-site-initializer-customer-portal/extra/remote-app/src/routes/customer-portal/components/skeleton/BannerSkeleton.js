import { useContext } from "react";
import TextSkeleton from '~/common/components/skeleton/text-skeleton';
import { AppContext } from "../../context";

const BannerSkeleton = () => {
  const [state] = useContext(AppContext);

  return (
    <div className="banner d-flex flex-column ml-5 rounded-left">
      <div className="d-flex flex-column h-100 justify-content-between m-3 p-5 text-neutral-10">
        <div className="display-4 font-weight-normal">
          <TextSkeleton height={50} width={500} />
        </div>

        <div>
          <TextSkeleton height={36} width={200} />

          <div className="pt-1">
            <TextSkeleton height={24} width={700} />
          </div>

        </div>
      </div>

      <img className="position-absolute" src={`${state.assetsPath}/assets/banner_background_customer_portal.svg`} />
    </div>

  );
};

export default BannerSkeleton;