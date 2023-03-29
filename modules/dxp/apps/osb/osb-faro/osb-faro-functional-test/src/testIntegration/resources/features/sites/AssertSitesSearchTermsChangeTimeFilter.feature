@spira_Sites @Sites @team_FARO @priority_3 @blocked
Feature: Assert Sites Search Terms change time filter
	As a Business User, I should be able to change time filter in Sites Search Terms

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Change time filter in Search Term
		Given I go to the "Sites" page
		When I click the "Last 30 days" dropdown in the "Search Terms" card
		And I click the "Last 24 hours" dropdown option
		Then I should see a search term named "automotive" in the table