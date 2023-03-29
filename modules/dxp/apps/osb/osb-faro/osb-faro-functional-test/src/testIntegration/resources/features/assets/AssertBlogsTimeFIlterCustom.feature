@spira_Assets @Assets @team_FARO @priority_5
Feature: Custom Range time filter in Blogs assets page
	As an Business User, I should be able to set a custom date range with the time filter on the Blogs page

	Background: [Setup] Navigate to Blogs assets page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Assets" page
		* I click the "Blogs" tab

	Scenario: Set a Custom Range time filter in Blogs assets page
		Given I click the "Last 30 days" dropdown in the card
		When I click the "Custom Range" button
		And I set start date "August 1 2020" and end date "August 10 2020"
		Then I should see time filter "Aug 1, 2020 - Aug 10, 2020" in card