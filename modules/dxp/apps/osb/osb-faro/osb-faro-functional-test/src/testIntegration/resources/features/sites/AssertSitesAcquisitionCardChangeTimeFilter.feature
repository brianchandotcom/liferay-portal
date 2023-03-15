@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Acquisition change time filter
	As a Business User, I should be able to change time filter in Sites Acquisition

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Change time filter in Acquisition
		Given I go to the "Sites" page
		And I click the "Referrers" button
		When I click the "Last 30 days" dropdown in the "Acquisitions" card
		And I click the "Last 24 hours" dropdown option
		Then I should see a referrer named "www.elliot-feeney.co" in the table