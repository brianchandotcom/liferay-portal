@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Top Pages change time filter
	As a Business User, I should be able to change time filter in Sites Top Pages

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Change time filter in Top Pages
		Given I go to the "Sites" page
		When I click the "Last 30 days" dropdown in the "Top Pages" card
		And I click the "Last 24 hours" dropdown option
		Then I should see a page named "enhance open-source e-tailers" in the table