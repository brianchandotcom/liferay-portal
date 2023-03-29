@spira_Search @Search @Segments @List @Profile @team_FARO @priority_3
Feature: Search the Segment Profile Engagement List
	As an Business User, I should be able to search a Segment Profile's Engagement List for an Individual

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "engineers" in the table

	Scenario: Search the Segment Profile's Engagement List
		Given I click the "Membership" tab
		And I click the "Segment Engagement Score" card tab
		When I search for "Tory Glover"
		Then I should only see an Individual named "Tory Glover" in the table