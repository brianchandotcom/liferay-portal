@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Interests change time filter
	As a Business User, I should be able to change time filter in Sites Interests

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Change time filter in Interests
		Given I go to the "Sites" page
		When I click the "Last 30 days" dropdown in the "Interests" card
		And I click the "Last 7 days" dropdown option
		Then I should see an interest named "mesh synergistic schemas" in the table