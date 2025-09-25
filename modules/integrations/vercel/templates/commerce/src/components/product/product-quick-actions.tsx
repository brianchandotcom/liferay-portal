import { Share } from "lucide-react";
import { Button } from "../ui/button";

export default function ProductQuickActions() {
  const handleShare = async () => {
    try {
      const url = window.location.href;

      await navigator.clipboard.writeText(url);

      alert("Link copied!\nThe page URL has been copied to your clipboard.");
    } catch {
      alert("Copy FAILED!\nWe couldn’t copy the link. Try again.");
    }
  };

  return (
    <Button
      className="w-full justify-start"
      onClick={handleShare}
      size="sm"
      variant="outline"
    >
      <Share className="h-2 w-2 mr-1" />
      Copy & Share
    </Button>
  );
}
